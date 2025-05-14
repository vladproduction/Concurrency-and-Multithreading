package com.vladproduction.parallelism_parallel_stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ParallelStreamExamples {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream().forEach(n -> System.out.print(n + " ")); //order preserved
        System.out.println("\n---------");
        numbers.parallelStream().forEach(n -> System.out.print(n + " ")); //order is not preserved

        System.out.println("\n---------");
        //Concurrent reduction:
        ConcurrentMap<Boolean, List<Integer>> groupedNumbers = numbers
                .parallelStream()
                .unordered()
                .collect(Collectors.groupingByConcurrent(n -> n % 2 == 0));
        System.out.println("Grouped numbers: " + groupedNumbers);

        Set<Collector.Characteristics> characteristics = Collectors
                .groupingByConcurrent(n -> "Java")
                .characteristics();
        System.out.println("Characteristics: " + characteristics);

        //Ordering:
        System.out.println("List of numbers:");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("List of numbers in reverse order:");
        Comparator<Integer> reverse = Comparator.reverseOrder();
        numbers.sort(reverse);
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("List of numbers with Parallel Stream:");
        numbers.parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("List of numbers with Another Parallel Stream:");
        numbers.parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("---------List of numbers with Parallel Stream and forEachOrdered:---------");
        numbers.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();

    }
}





















