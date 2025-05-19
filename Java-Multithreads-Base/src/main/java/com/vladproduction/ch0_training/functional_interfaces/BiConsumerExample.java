package com.vladproduction.ch0_training.functional_interfaces;

import java.util.function.BiConsumer;

public class BiConsumerExample {
    public static void main(String[] args) {
        BiConsumer<String, Integer> printInfo = (name, age) ->
            System.out.println(name + " is " + age + " years old.");

        printInfo.accept("Alice", 30); // Output: Alice is 30 years old.

        // method signature is used for void return type.
        // (U, T) -> void;
    }
}