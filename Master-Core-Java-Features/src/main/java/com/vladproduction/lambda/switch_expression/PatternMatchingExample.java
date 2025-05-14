package com.vladproduction.lambda.switch_expression;

public class PatternMatchingExample {
    public static void main(String[] args) {

        //instanceof is used:
        instanceOfUsed();

        //Pattern Matching for switch used:
        patternMatching();

    }

    private static void instanceOfUsed() {
        //Object obj = 123;
        Object obj = "123";
        String message = "";
        if(obj instanceof Integer){
            message = obj + " is an Integer.";
        }
        else if(obj instanceof String){
            message = obj + " is an String.";
        }
        else{
            message = obj + " is not an Integer or String.";
        }
        System.out.println(message);
    }

    private static void patternMatching() {
        Object obj = 123;
//        Object obj = "123";
        String message = "";

        message = switch (obj){
            case Integer i -> obj + " is an Integer.";
            case String i -> i + " is an String.";
            default -> obj + " is not an Integer or String.";
        };
        System.out.println(message);
    }
}
