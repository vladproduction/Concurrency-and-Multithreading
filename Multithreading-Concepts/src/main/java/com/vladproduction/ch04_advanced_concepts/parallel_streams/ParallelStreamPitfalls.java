package com.vladproduction.ch04_advanced_concepts.parallel_streams;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamPitfalls {

    public static void main(String[] args) {
        // Pitfall 1: Non-associative operations
        demonstrateNonAssociativeOperation();

        // Pitfall 2: State modification
        demonstrateStatefulOperation();

        // Pitfall 3: Using LinkedList with parallel streams
        compareCollectionTypes();

        // Pitfall 4: Inappropriate small workload
        demonstrateSmallWorkload();

        // Pitfall 5: Side effects
        demonstrateSideEffects();

        // Pitfall 6: Using spliterator not optimized for splitting
        demonstrateSplittingEfficiency();
    }

    private static void demonstrateNonAssociativeOperation() {
        System.out.println("=== Non-associative Operation Example ===");

        // String concatenation is non-associative: (a+b)+c ≠ a+(b+c)
        String result1 = IntStream.range(0, 100)
                .mapToObj(Integer::toString)
                .reduce("", (a, b) -> a + b);

        String result2 = IntStream.range(0, 100)
                .parallel()
                .mapToObj(Integer::toString)
                .reduce("", (a, b) -> a + b);

        System.out.println("Sequential result length: " + result1.length());
        System.out.println("Parallel result length: " + result2.length());
        System.out.println("Results match: " + result1.equals(result2));

        // Better approach for strings: use joining collector
        String result3 = IntStream.range(0, 100)
                .parallel()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        System.out.println("Using joining collector: " + (result1.equals(result3)));
    }

    private static void demonstrateStatefulOperation() {
        System.out.println("\n=== Stateful Operation Example ===");

        // Incorrect: Using stateful lambda in a parallel stream
        AtomicInteger counter = new AtomicInteger();
        IntStream.range(0, 1000)
                .parallel()
                .forEach(i -> counter.incrementAndGet());

        System.out.println("Final counter value should be 1000, actual: " + counter.get());

        // Correct: Using proper reduction
        int sum = IntStream.range(0, 1000)
                .parallel()
                .sum();

        System.out.println("Sum using proper reduction: " + sum);

        // Another incorrect example: stateful filter
        Random random = new Random();
        List<Integer> numbers = IntStream.range(0, 100)
                .boxed()
                .collect(Collectors.toList());

        // This might select different elements in parallel vs sequential
        List<Integer> selected = new ArrayList<>();
        numbers.parallelStream()
                .filter(i -> {
                    boolean select = random.nextBoolean();
                    if (select) selected.add(i);
                    return select;
                })
                .count(); // Terminal operation to execute the stream

        System.out.println("Incorrectly selected items: " + selected.size());

        // Correct approach using collect
        List<Integer> selectedCorrectly = numbers.parallelStream()
                .filter(i -> random.nextBoolean())
                .collect(Collectors.toList());

        System.out.println("Correctly selected items: " + selectedCorrectly.size());
    }

    private static void compareCollectionTypes() {
        System.out.println("\n=== Collection Type Performance Example ===");

        int size = 1_000_000;

        // ArrayList - good for parallel processing (efficient splitting)
        List<Integer> arrayList = new ArrayList<>(size);

        // LinkedList - poor for parallel processing (inefficient splitting)
        List<Integer> linkedList = new LinkedList<>();

        // Fill the lists
        for (int i = 0; i < size; i++) {
            Integer value = i;
            arrayList.add(value);
            linkedList.add(value);
        }

        // Test ArrayList
        long start = System.currentTimeMillis();
        long sumArrayList = arrayList.parallelStream().mapToLong(Integer::longValue).sum();
        long timeArrayList = System.currentTimeMillis() - start;

        // Test LinkedList
        start = System.currentTimeMillis();
        long sumLinkedList = linkedList.parallelStream().mapToLong(Integer::longValue).sum();
        long timeLinkedList = System.currentTimeMillis() - start;

        System.out.println("ArrayList parallel sum time: " + timeArrayList + " ms");
        System.out.println("LinkedList parallel sum time: " + timeLinkedList + " ms");
        System.out.println("LinkedList/ArrayList time ratio: " +
                ((double) timeLinkedList / timeArrayList));
    }

    private static void demonstrateSmallWorkload() {
        System.out.println("\n=== Small Workload Example ===");

        // Small data set - overhead of parallelism likely exceeds benefits
        List<Integer> smallList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        long start = System.currentTimeMillis();
        int sumSequential = smallList.stream().mapToInt(Integer::intValue).sum();
        long timeSequential = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        int sumParallel = smallList.parallelStream().mapToInt(Integer::intValue).sum();
        long timeParallel = System.currentTimeMillis() - start;

        System.out.println("Small list sequential sum time: " + timeSequential + " ms");
        System.out.println("Small list parallel sum time: " + timeParallel + " ms");

        // Larger workload per element makes parallelism more beneficial
        start = System.currentTimeMillis();
        int complexSumSequential = smallList.stream()
                .mapToInt(i -> {
                    // Simulate complex computation
                    int result = 0;
                    for (int j = 0; j < 1000000; j++) {
                        result += (i * j) % 10;
                    }
                    return result;
                }).sum();
        long timeComplexSequential = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        int complexSumParallel = smallList.parallelStream()
                .mapToInt(i -> {
                    // Same complex computation
                    int result = 0;
                    for (int j = 0; j < 1000000; j++) {
                        result += (i * j) % 10;
                    }
                    return result;
                }).sum();
        long timeComplexParallel = System.currentTimeMillis() - start;

        System.out.println("Complex computation sequential time: " +
                timeComplexSequential + " ms");
        System.out.println("Complex computation parallel time: " +
                timeComplexParallel + " ms");
        System.out.println("Speedup with complex tasks: " +
                ((double) timeComplexSequential / timeComplexParallel));
    }

    private static void demonstrateSideEffects() {
        System.out.println("\n=== Side Effects Example ===");

        // Bad practice: Side effects in parallel stream
        List<Integer> numbers = IntStream.range(0, 10000).boxed()
                .collect(Collectors.toList());
        List<Integer> synchronizedList =
                Collections.synchronizedList(new ArrayList<>());

        // Even with synchronized collection, order is not predictable
        numbers.parallelStream().forEach(synchronizedList::add);

        System.out.println("List size after parallel add: " + synchronizedList.size());
        System.out.println("First 10 elements (order likely scrambled): " +
                synchronizedList.subList(0, 10));

        // Better practice: Use collect operation
        List<Integer> collectResult = numbers.parallelStream()
                .collect(Collectors.toList());

        System.out.println("List size after parallel collect: " + collectResult.size());

        // Another example: side effects causing incorrect results
        int[] total = new int[1];
        numbers.parallelStream().forEach(n -> total[0] += n);

        int expectedSum = numbers.stream().mapToInt(i -> i).sum();
        System.out.println("Sum with side effects: " + total[0]);
        System.out.println("Expected sum: " + expectedSum);
        System.out.println("Results match: " + (total[0] == expectedSum));

        // Correct approach
        int correctSum = numbers.parallelStream().reduce(0, Integer::sum);
        System.out.println("Sum with reduction: " + correctSum);
    }

    private static void demonstrateSplittingEfficiency() {
        System.out.println("\n=== Spliterator Efficiency Example ===");

        // Create a custom spliterator that's inefficient for splitting
        List<Integer> numbers = IntStream.range(0, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        // Wrap in unmodifiable to simulate a poor spliterator without actual implementation
        List<Integer> poorSplitting = Collections.unmodifiableList(
                new LinkedList<>(numbers));

        // Regular ArrayList - good splitting
        List<Integer> goodSplitting = new ArrayList<>(numbers);

        long start = System.currentTimeMillis();
        long sumPoor = poorSplitting.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        long timePoor = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        long sumGood = goodSplitting.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        long timeGood = System.currentTimeMillis() - start;

        System.out.println("Time with poor splitting: " + timePoor + " ms");
        System.out.println("Time with good splitting: " + timeGood + " ms");
        System.out.println("Poor/Good time ratio: " + ((double) timePoor / timeGood));

        // Sequential for comparison
        start = System.currentTimeMillis();
        long sumSeq = goodSplitting.stream()
                .mapToLong(Integer::longValue)
                .sum();
        long timeSeq = System.currentTimeMillis() - start;

        System.out.println("Sequential time: " + timeSeq + " ms");
        System.out.println("Poor parallel vs sequential ratio: " +
                ((double) timePoor / timeSeq));
    }

}
