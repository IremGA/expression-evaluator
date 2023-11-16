package com.leapwise.expressionevaluator.service;

import com.leapwise.expressionevaluator.model.ExpressionIdentifier;

import java.util.List;

public interface ExpressionService {
    public List getAllExpressions();

    public ExpressionIdentifier getExpressionById(String expression_identifier_id);

    public ExpressionIdentifier saveExpressionIdentifier(ExpressionIdentifier expressionIdentifier);

    public void deleteExpressionIdentifier(String expression_identifier_id);
}
