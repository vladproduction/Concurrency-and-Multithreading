package com.vladproduction.lambda.switch_expression;

public class EnumWithSwitch {
    public static void main(String[] args) {

        Days day = Days.WEDNESDAY;

        String message = switch (day){
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Work day";
            case SATURDAY, SUNDAY -> "Weekend";
            default -> "Invalid day";
        };
        System.out.println("message: " + message);

    }

    enum Days{
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

}
