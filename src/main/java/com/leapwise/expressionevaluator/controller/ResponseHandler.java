package com.leapwise.expressionevaluator.controller;

import com.leapwise.expressionevaluator.constant.ExpressionConstant;
import com.leapwise.expressionevaluator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put(ExpressionConstant.MESSAGE, message);
        map.put(ExpressionConstant.STATUS, status.value());
        map.put(ExpressionConstant.DATA, responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateExceptionResponse(ErrorResponse errorResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put(ExpressionConstant.ERROR_CODE, errorResponse.getErrorCode());
        map.put(ExpressionConstant.MESSAGE, errorResponse.getMessage());
        map.put(ExpressionConstant.REASON, errorResponse.getCause());

        return new ResponseEntity<Object>(map,errorResponse.getStatus());
    }
}
