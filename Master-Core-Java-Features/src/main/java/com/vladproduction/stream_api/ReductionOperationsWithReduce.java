package com.vladproduction.stream_api;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReductionOperationsWithReduce {
    public static void main(String[] args) {

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        Optional<Integer> sum = nums.stream()
                .reduce((a, b) -> a + b);
        sum.ifPresent(n -> System.out.println("Sum: " + n));

        int sumWithInitialValue = nums.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum sumWithInitialValue: " + sumWithInitialValue);

        int elementProduct = nums.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("Result elementProduct: " + elementProduct);

        Optional<Integer> biggest = nums.stream()
                .reduce((a, b) -> a > b ? a : b);
        biggest.ifPresent(n -> System.out.println("Biggest: " + n));

        Optional<Integer> smallest = nums.stream()
                .reduce((a, b) -> a < b ? a : b);
        smallest.ifPresent(n -> System.out.println("Smallest: " + n));


    }
}
