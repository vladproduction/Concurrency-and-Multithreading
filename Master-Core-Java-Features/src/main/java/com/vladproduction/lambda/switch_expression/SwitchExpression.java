package com.vladproduction.lambda.switch_expression;

public class SwitchExpression {
    public static void main(String[] args) {


        simpleSwitch();

        switchReturnMethod();

        yieldUsedMethod();

        yieldUsedMethod2();


    }

    private static void simpleSwitch() {
        int day = 10;
        String message = "";
        switch (day) {
            case 1, 2, 3, 4, 5:
                message = "Work day";
                break;
            case 6, 7:
                message = "Weekend";
                break;
            default:
                message = "Invalid day";
        }
        System.out.println("message: " + message);
    }

    private static void switchReturnMethod() {
        int day = 10;
        String message = "";
        message = switch (day) {
            case 1, 2, 3, 4, 5 -> "Work day";
            case 6, 7 -> "Weekend";
            default -> "Invalid day";
        };
        System.out.println("message: " + message);
    }

    private static void yieldUsedMethod() {
        int day = 1;
        String message = "";
        message = switch (day) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Case day number is: 1, 2, 3, 4, 5 -> ");
                yield "Work day";
            }
            case 6, 7 -> {
                System.out.println("Case day number is: 6, 7 -> ");
                yield "Weekend";
            }
            default -> "Invalid day";
        };
        System.out.println("message: " + message);
    }

    private static void yieldUsedMethod2(){
        int day = 3;
        String message = "";
        message = switch (day){
            case 1, 2, 3, 4, 5 : yield "Work day";
            case 6, 7 : yield "Weekend";
            default : yield "Invalid day";
        };
        System.out.println("message: " + message);


    }
}















