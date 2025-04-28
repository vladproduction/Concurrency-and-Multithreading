package com.vladproduction.ch13_synchronized_and_blocks;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class demonstrates the behavior of StringBuilder (not thread-safe) and
 * StringBuffer (thread-safe) in a multithreaded environment.
 *
 * Key differences:
 * - StringBuilder: Faster but not synchronized - unsafe for concurrent access
 * - StringBuffer: Thread-safe due to synchronized methods, but slower performance
 */
public class StringBuilderVsStringBufferDemo {

    // Number of threads to use
    private static final int THREAD_COUNT = 10;

    // Number of append operations per thread
    private static final int OPERATIONS_PER_THREAD = 1000;

    // Content to append
    private static final String CONTENT = "Java";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting multithreaded comparison of StringBuilder vs StringBuffer");

        // Test the thread-unsafe StringBuilder
        demonstrateStringBuilder();

        // Test the thread-safe StringBuffer
        demonstrateStringBuffer();

        // Performance comparison
        comparePerformance();
    }

    /**
     * Demonstrates how StringBuilder is NOT thread-safe and can lead to data corruption
     * when accessed by multiple threads simultaneously
     */
    private static void demonstrateStringBuilder() throws InterruptedException {
        System.out.println("\n=== StringBuilder (Not Thread-Safe) ===");

        // Results may vary between runs due to race conditions
        StringBuilder stringBuilder = new StringBuilder();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // Create and start multiple threads to append to the StringBuilder
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        stringBuilder.append(CONTENT);
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // Wait for all threads to complete
        latch.await();

        // Calculate expected length
        int expectedLength = THREAD_COUNT * OPERATIONS_PER_THREAD * CONTENT.length();
        int actualLength = stringBuilder.length();

        System.out.println("Expected length: " + expectedLength);
        System.out.println("Actual length: " + actualLength);
        System.out.println("Data corruption occurred: " + (expectedLength != actualLength));
    }

    /**
     * Demonstrates how StringBuffer is thread-safe due to synchronized methods,
     * ensuring data integrity when accessed by multiple threads
     */
    private static void demonstrateStringBuffer() throws InterruptedException {
        System.out.println("\n=== StringBuffer (Thread-Safe) ===");

        StringBuffer stringBuffer = new StringBuffer();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // Create and start multiple threads to append to the StringBuffer
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        stringBuffer.append(CONTENT);
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // Wait for all threads to complete
        latch.await();

        // Calculate expected length
        int expectedLength = THREAD_COUNT * OPERATIONS_PER_THREAD * CONTENT.length();
        int actualLength = stringBuffer.length();

        System.out.println("Expected length: " + expectedLength);
        System.out.println("Actual length: " + actualLength);
        System.out.println("Data corruption occurred: " + (expectedLength != actualLength));
    }

    /**
     * Compares performance between StringBuilder and StringBuffer in both
     * single-threaded and multi-threaded scenarios
     */
    private static void comparePerformance() throws InterruptedException {
        System.out.println("\n=== Performance Comparison ===");

        // Single-threaded comparison
        System.out.println("\nSingle-threaded performance:");

        // StringBuilder (single thread)
        long startTime = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < THREAD_COUNT * OPERATIONS_PER_THREAD; i++) {
            sb.append(CONTENT);
        }
        long sbTime = System.nanoTime() - startTime;

        // StringBuffer (single thread)
        startTime = System.nanoTime();
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < THREAD_COUNT * OPERATIONS_PER_THREAD; i++) {
            sbuf.append(CONTENT);
        }
        long sbufTime = System.nanoTime() - startTime;

        System.out.println("StringBuilder time: " + sbTime / 1_000_000.0 + " ms");
        System.out.println("StringBuffer time: " + sbufTime / 1_000_000.0 + " ms");
        System.out.println("StringBuilder is " + (sbufTime / (double) sbTime) + "x faster in single-threaded operation");

        // Multi-threaded comparison with explicit synchronization for StringBuilder
        System.out.println("\nMulti-threaded performance with explicit synchronization:");

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        final Object lock = new Object();

        // StringBuilder with explicit synchronization
        final StringBuilder sbConcurrent = new StringBuilder();
        startTime = System.nanoTime();

        CountDownLatch sbLatch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        synchronized (lock) {
                            sbConcurrent.append(CONTENT);
                        }
                    }
                } finally {
                    sbLatch.countDown();
                }
            });
        }
        sbLatch.await();
        long sbConcurrentTime = System.nanoTime() - startTime;

        // StringBuffer (inherently thread-safe)
        final StringBuffer sbufConcurrent = new StringBuffer();
        startTime = System.nanoTime();

        CountDownLatch sbufLatch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        sbufConcurrent.append(CONTENT);
                    }
                } finally {
                    sbufLatch.countDown();
                }
            });
        }
        sbufLatch.await();
        long sbufConcurrentTime = System.nanoTime() - startTime;

        System.out.println("StringBuilder with external synchronization: " + sbConcurrentTime / 1_000_000.0 + " ms");
        System.out.println("StringBuffer with internal synchronization: " + sbufConcurrentTime / 1_000_000.0 + " ms");

        // Compare and explain the results
        if (sbufConcurrentTime < sbConcurrentTime) {
            System.out.println("StringBuffer is faster in this multi-threaded scenario - " +
                    "its internal synchronization has less overhead than explicit locking.");
        } else {
            System.out.println("StringBuilder with explicit synchronization is faster in this scenario - " +
                    "but requires careful manual thread safety management.");
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\nConclusion:");
        System.out.println("- StringBuilder: Faster but requires external synchronization for thread safety");
        System.out.println("- StringBuffer: Thread-safe by default but with some performance overhead");
        System.out.println("- For single-threaded use: Always prefer StringBuilder");
        System.out.println("- For multi-threaded use: StringBuffer is safer and simpler, but synchronized StringBuilder may be faster in specific scenarios");
    }

}
