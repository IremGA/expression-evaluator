package com.leapwise.expressionevaluator.service;

import com.leapwise.expressionevaluator.model.Customer;

import java.io.IOException;

public interface EvaluateService {

    boolean evaluateExpression(String expression, String value) throws IOException;
}
