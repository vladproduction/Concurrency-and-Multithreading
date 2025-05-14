package com.vladproduction.lambda.simple_calculator_with_lambda;

import java.util.Scanner;

public class RunnerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter first number: ");
        double firstNumber = scanner.nextDouble();
        System.out.println("Please enter second number: ");
        double secondNumber = scanner.nextDouble();

        Calculator calculator = new Calculator();
        calculator.calculate(firstNumber, secondNumber, (x, y) -> {

            System.out.println(x + " + " + y + " = " + (x + y));
            System.out.println(x + " - " + y + " = " + (x - y));
            System.out.println(x + " * " + y + " = " + (x * y));

            if(y == 0){
                System.out.println(x + " / " + y + " = " + "Divisor cannot be zero!");
            }else {
                System.out.println(x + " / " + y + " = " + (x / y));
            }

        });

        scanner.close();
    }
}
