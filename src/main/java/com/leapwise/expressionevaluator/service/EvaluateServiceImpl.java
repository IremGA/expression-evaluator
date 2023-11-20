package com.leapwise.expressionevaluator.service;

import com.google.gson.Gson;
import com.leapwise.expressionevaluator.constant.ExpressionConstant;
import com.leapwise.expressionevaluator.exception.ExpressionException;
import com.leapwise.expressionevaluator.model.Customer;
import com.leapwise.expressionevaluator.model.ExpressionIdentifier;
import com.leapwise.expressionevaluator.repository.ExpressionRepository;
import com.leapwise.expressionevaluator.util.ExpressionValidator;
import com.leapwise.expressionevaluator.util.JsonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private ExpressionValidator expressionValidator;

    @Autowired
    ExpressionRepository expressionRepository;

    @Autowired
    private JsonValidator jsonValidator;

    @Override
    public boolean evaluateExpression(String expression_name, String value) throws IOException {

        //Make necessary Validations

        ExpressionIdentifier expressionIdentifierGet = expressionRepository.findAll().stream().filter(expression -> expression_name.equals(expression.getName())).findAny().orElse(null);

        if (expressionIdentifierGet == null) {
            throw new ExpressionException(HttpStatus.BAD_REQUEST.toString(), ExpressionConstant.EXPRESSION_NOT_FOUND, ExpressionConstant.EXPRESSION_NOT_FOUND_REASON, HttpStatus.BAD_REQUEST);
        }

        boolean isValidJson = jsonValidator.isValid(value);
        if (!isValidJson) {
            throw new ExpressionException(HttpStatus.BAD_REQUEST.toString(), ExpressionConstant.JSON_IS_NOT_VALID_MESSAGE, ExpressionConstant.JSON_IS_NOT_VALID, HttpStatus.BAD_REQUEST);
        }

        Gson gson = new Gson();
        //Later can be more generic
        Object jsonObject = gson.fromJson(value, Customer.class);

        return expressionValidator.validateLogicalExpression(expressionIdentifierGet.getValue(), jsonObject);

    }
}
