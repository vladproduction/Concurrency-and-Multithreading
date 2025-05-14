package com.vladproduction.lambda.simple_calculator_with_lambda;

public class Calculator {

    public void calculate(double x, double y, Operation operation){
        operation.performOperation(x, y);
    }

}
