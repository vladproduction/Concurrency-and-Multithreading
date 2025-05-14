package com.vladproduction.lambda.predicate;

import java.util.Scanner;
import java.util.function.Predicate;

public class SimplePredicateNumberTestingApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int number = scanner.nextInt();

        if(isEven(number, (n) -> n % 2 == 0)){
            System.out.println("The number is even: " + number);
        }
        else {
            System.out.println("The number is odd: " + number);
        }
        scanner.close();

    }

    public static boolean isEven(int number, Predicate<Integer> p){
        return p.test(number);
    }

}
