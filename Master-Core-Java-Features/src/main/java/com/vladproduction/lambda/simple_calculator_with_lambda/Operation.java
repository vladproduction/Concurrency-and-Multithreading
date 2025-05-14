package com.vladproduction.lambda.simple_calculator_with_lambda;

@FunctionalInterface
public interface Operation {

    void performOperation(double x, double y);

}
