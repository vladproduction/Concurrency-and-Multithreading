package com.vladproduction.ch0_training_tasks_core;

public class MainOverloadingDemo {
    // This is the entry point that JVM will call
    public static void main(String[] args) {
        System.out.println("Standard main method called");

        // Call the overloaded main method from here
        main(10);
        main("Hello", "World");
    }

    // Overloaded main methods
    public static void main(int number) {
        System.out.println("Overloaded main with int: " + number);
    }

    public static void main(String arg1, String arg2) {
        System.out.println("Overloaded main with two strings: " + arg1 + ", " + arg2);
    }
}
