package com.leapwise.expressionevaluator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExpressionException extends RuntimeException {

    private String code;
    
    private String message;
    
    private String reason;

    private HttpStatus httpStatus;

    
}
