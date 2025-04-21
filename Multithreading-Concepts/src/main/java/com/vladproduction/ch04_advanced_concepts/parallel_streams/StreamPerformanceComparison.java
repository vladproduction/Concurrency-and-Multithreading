package com.vladproduction.ch04_advanced_concepts.parallel_streams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class StreamPerformanceComparison {

    // CPU-intensive operation
    private static long fibonacci(long n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        // Generate test data - numbers 1 to 40
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            numbers.add(i);
        }

        // Test scenarios
        System.out.println("CPU-bound task (calculating Fibonacci):");
        runTest(numbers, n -> fibonacci(n % 40));

        System.out.println("\nIO-bound simulation (Thread.sleep):");
        runTest(numbers, n -> {
            try {
                Thread.sleep(10); // Simulate IO operation
                return n;
            } catch (InterruptedException e) {
                return 0;
            }
        });

        System.out.println("\nMutation test (incorrect approach):");
        testMutation();

        System.out.println("\nOrder test:");
        testOrder();

        System.out.println("\nTesting different collection sizes:");
        testWithDifferentSizes();
    }

    interface Operation<T, R> {
        R apply(T t);
    }

    private static <T, R> void runTest(List<T> data, Operation<T, R> op) {
        // Sequential
        long start = System.currentTimeMillis();
        List<R> seqResult = data.stream()
                .map(n -> op.apply(n))
                .collect(Collectors.toList());
        long seqTime = System.currentTimeMillis() - start;

        // Parallel
        start = System.currentTimeMillis();
        List<R> parResult = data.parallelStream()
                .map(n -> op.apply(n))
                .collect(Collectors.toList());
        long parTime = System.currentTimeMillis() - start;

        System.out.println("Sequential time: " + seqTime + " ms");
        System.out.println("Parallel time: " + parTime + " ms");
        System.out.println("Speedup: " + (seqTime > 0 ? (float)seqTime / parTime : "N/A"));
    }

    private static void testMutation() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }

        // Incorrect approach - mutation in parallel stream
        List<Integer> badResults = new ArrayList<>();
        numbers.parallelStream().forEach(badResults::add);
        System.out.println("ArrayList size after parallel mutation: " + badResults.size());

        // Correct approach 1 - use thread-safe collection
        List<Integer> goodResults1 = new CopyOnWriteArrayList<>();
        numbers.parallelStream().forEach(goodResults1::add);
        System.out.println("CopyOnWriteArrayList size: " + goodResults1.size());

        // Correct approach 2 - use collect instead of forEach
        List<Integer> goodResults2 = numbers.parallelStream()
                .collect(Collectors.toList());
        System.out.println("Collected result size: " + goodResults2.size());
    }

    private static void testOrder() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        System.out.println("Sequential ordered stream:");
        numbers.stream()
                .peek(i -> System.out.print(i + " "))
                .collect(Collectors.toList());

        System.out.println("\n\nParallel ordered stream:");
        numbers.parallelStream()
                .peek(i -> System.out.print(i + " "))
                .collect(Collectors.toList());

        System.out.println("\n\nParallel unordered stream:");
        numbers.parallelStream()
                .unordered()
                .peek(i -> System.out.print(i + " "))
                .collect(Collectors.toList());

        System.out.println("\n\nParallel with forEachOrdered:");
        numbers.parallelStream()
                .forEachOrdered(i -> System.out.print(i + " "));
    }

    private static void testWithDifferentSizes() {
        for (int size : new int[] {100, 1000, 10000, 100000, 1000000}) {
            List<Integer> data = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                data.add(i);
            }

            System.out.println("\nSize: " + size);

            // Simple map operation
            long start = System.currentTimeMillis();
            data.stream().map(n -> n * 2).count();
            long seqTime = System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            data.parallelStream().map(n -> n * 2).count();
            long parTime = System.currentTimeMillis() - start;

            System.out.println("  Simple map - Sequential: " + seqTime +
                    " ms, Parallel: " + parTime + " ms");
        }
    }

}
