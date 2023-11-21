package com.leapwise.expressionevaluator.evaluatorObject.impl;

import com.google.gson.Gson;
import com.leapwise.expressionevaluator.evaluatorObject.Evaluator;
import com.leapwise.expressionevaluator.model.Customer;
import com.leapwise.expressionevaluator.util.ExpressionValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomerEvaluator implements Evaluator {

    @Override
    public boolean evaluateExpression(String expression, String value) {

        Gson gson = new Gson();
        Object jsonObject = gson.fromJson(value, Customer.class);
        return ExpressionValidator.validateLogicalExpression(expression, jsonObject);
    }
}
