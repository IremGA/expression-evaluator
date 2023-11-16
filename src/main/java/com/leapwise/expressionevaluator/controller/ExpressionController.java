package com.leapwise.expressionevaluator.controller;

import com.leapwise.expressionevaluator.constant.ExpressionConstant;
import com.leapwise.expressionevaluator.exception.ErrorResponse;
import com.leapwise.expressionevaluator.exception.ExpressionException;
import com.leapwise.expressionevaluator.model.ExpressionIdentifier;
import com.leapwise.expressionevaluator.service.ExpressionService;
import com.leapwise.expressionevaluator.util.SystemTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExpressionController {
    @Autowired
    ExpressionService expressionService;

    @Autowired
    @Qualifier("systemTokenStore")
    private SystemTokenStore systemTokenStore;

    private static final Logger Expression_Logger = LoggerFactory.getLogger(ExpressionController.class);

    @PostMapping("/expression")
    private ResponseEntity createEmployee(@RequestBody ExpressionIdentifier expressionIdentifier,@RequestHeader Map<String,String> headers) {
        try{


            String username = headers.get(ExpressionConstant.IAM_PASSWORD);
            String password = headers.get(ExpressionConstant.IAM_PASSWORD);
            String grant_type = headers.get(ExpressionConstant.GRANT_TYPE);
            String client_id = headers.get(ExpressionConstant.CLIENT_ID);
            String client_secret = headers.get(ExpressionConstant.CLIENT_SECRET);

            String accessToken = systemTokenStore.getAccessToken(username, password, grant_type, client_id, client_secret);
            Expression_Logger.info("Saving expression operation started");
            ExpressionIdentifier expression = new ExpressionIdentifier();
            if(accessToken != null){
                expression= expressionService.saveExpressionIdentifier(expressionIdentifier);
            }
            return ResponseHandler.generateResponse(ExpressionConstant.EXPRESSION_SUCCESSFULLY_CREATED, HttpStatus.CREATED, expression);

        } catch(ExpressionException expressionException){

            Expression_Logger.info("ExpressionException thrown while saving expression");
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(expressionException.getCode());
            errorResponse.setMessage(expressionException.getMessage());
            errorResponse.setCause(expressionException.getReason());
            errorResponse.setStatus(expressionException.getHttpStatus());
            return ResponseHandler.generateExceptionResponse(errorResponse);

        }catch (Exception exception) {

            Expression_Logger.info("Unknown Error thrown while saving expression");
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorResponse.setMessage(ExpressionConstant.ERROR_SAVING_EXPRESSION);
            errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            errorResponse.setCause(exception.getMessage());
            return ResponseHandler.generateExceptionResponse(errorResponse);
        }
    }

}
