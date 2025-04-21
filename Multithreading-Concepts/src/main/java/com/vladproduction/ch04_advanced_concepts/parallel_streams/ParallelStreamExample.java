package com.vladproduction.ch04_advanced_concepts.parallel_streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallelStreamExample {

    public static void main(String[] args) {
        // Creating a parallel stream from an IntStream
        long count = IntStream.range(1, 1_000_000)
                .parallel()
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println("Number of even numbers: " + count);

        // Converting a sequential stream to parallel
        List<String> names = Arrays.asList("John", "Jane", "Adam", "Eve",
                "Michael", "Sarah", "David", "Emma");

        long startTime = System.nanoTime();
        String result1 = names.stream()
                .map(String::toUpperCase)
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        long endTime = System.nanoTime();
        System.out.println("Sequential time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        String result2 = names.parallelStream()
                .map(String::toUpperCase)
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        endTime = System.nanoTime();
        System.out.println("Parallel time: " + (endTime - startTime) + " ns");

        // Processing a large array in parallel
        int[] numbers = new int[10_000_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        startTime = System.nanoTime();
        int sum1 = Arrays.stream(numbers).sum();
        endTime = System.nanoTime();
        System.out.println("Sequential sum: " + sum1);
        System.out.println("Sequential time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        int sum2 = Arrays.stream(numbers).parallel().sum();
        endTime = System.nanoTime();
        System.out.println("Parallel sum: " + sum2);
        System.out.println("Parallel time: " + (endTime - startTime) + " ns");
    }

}
