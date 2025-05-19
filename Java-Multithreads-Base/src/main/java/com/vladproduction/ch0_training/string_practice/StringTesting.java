package com.vladproduction.ch0_training.string_practice;

public class StringTesting {
    public static void main(String[] args) {

        String helloStr = "Hello";
        helloStr.concat(" friend!");
        System.out.println("helloStr = " + helloStr);

        System.out.println("-------------------");
        StringBuffer numberRange = new StringBuffer("4321");
        numberRange.reverse();
        System.out.println(numberRange);

    }
}
