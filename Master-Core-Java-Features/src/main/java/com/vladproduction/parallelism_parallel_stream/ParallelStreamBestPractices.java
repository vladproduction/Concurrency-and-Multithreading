package com.vladproduction.parallelism_parallel_stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamBestPractices {
    public static void main(String[] args) {

        //create a large list:
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000).boxed().toList();

        // GOOD PRACTICE 1: Use thread-safe collections with parallel streams
        // Bad example - not thread safe
        List<Integer> nonThreadSafeList = new ArrayList<>();

        // DO NOT DO THIS with parallel streams
        // numbers.parallelStream().forEach(nonThreadSafeList::add);

        // Good example - using a collector instead
        List<Integer> threadSafeCollection = numbers
                .parallelStream()
                .filter(n -> n % 2 == 0)
                .toList();

        // GOOD PRACTICE 2: Use unordered operations when order doesn't matter
        long count = numbers.parallelStream()
                .unordered() // hint that order doesn't matter
                .filter(n -> n % 10 == 0)
                .count();

        System.out.println("Count of numbers divisible by 10: " + count);

        // GOOD PRACTICE 3: Use atomic variables for accumulators
        AtomicLong atomicSum = new AtomicLong(0);
        numbers.parallelStream()
                .forEach(atomicSum::addAndGet);
        System.out.println("Sum of numbers: " + atomicSum.get());

        // Better approach: use built-in reduction
        long sum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        System.out.println("Sum using built-in reduction: " + sum);

        // GOOD PRACTICE 4: Use thread-safe maps for collecting results
        ConcurrentHashMap<Integer, Long> remainderCounts = new ConcurrentHashMap<>();

        numbers.parallelStream()
                .forEach(n -> {
                    int remainder = n % 10;
                    remainderCounts.merge(remainder, 1L, Long::sum);
                });
        System.out.println("\nCounts by remainder when divided by 10:");
        remainderCounts.forEach((remainder, countValue) ->
                System.out.println("Remainder " + remainder + ": " + countValue));


    }
}






















