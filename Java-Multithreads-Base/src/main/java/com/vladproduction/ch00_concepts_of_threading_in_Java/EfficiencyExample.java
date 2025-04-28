package com.vladproduction.ch00_concepts_of_threading_in_Java;

import java.util.stream.IntStream;

/**
 * Approach #3: Efficiency of Threading
 * Concept:
 * Creating and managing threads is more efficient than creating separate processes due to lower overhead and faster context switching.
 *
 * Code Example:
 * Below is a simple comparison (indirectly) showing how be might create multiple threads versus processes in a traditional sense.
 * Note that Java primarily uses threads, so we won't implement actual process creation here,
 * but we will observe the efficiency in a high load situation.
 * */
public class EfficiencyExample {
    public static void main(String[] args) {
        // Let's create and execute 10 threads
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Simulating processing time
                    System.out.println("Thread-Execution: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        });

        // In this code, multiple threads are created and executed in a loop.
        // Each thread simulates some work by sleeping for 1 second.
        // This illustrates the efficiency and speed of using threads to handle tasks concurrently
        // without the overhead of process management.
    }
}
