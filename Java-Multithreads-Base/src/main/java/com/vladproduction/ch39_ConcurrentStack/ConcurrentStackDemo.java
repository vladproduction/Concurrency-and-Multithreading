package com.vladproduction.ch39_ConcurrentStack;

import java.util.EmptyStackException;

/**
 * Demonstrates the concurrent stack with multiple producer and consumer threads.
 */
public class ConcurrentStackDemo {

    private static final int NUM_THREADS = 5;
    private static final int OPERATIONS_PER_THREAD = 100;
    private static final ConcurrentStack<Integer> stack = new ConcurrentStack<>();

    public static void main(String[] args) throws InterruptedException {
        // Start producer threads
        Thread[] producers = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    int value = threadId * 1000 + j;
                    stack.push(value);
                    System.out.println("Thread-" + threadId + " pushed: " + value);

                    // Simulate some work
                    try {
                        Thread.sleep((long) (Math.random() * 5));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            producers[i].start();
        }

        // Let producers work for a while
        Thread.sleep(500);

        // Start consumer threads
        Thread[] consumers = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    try {
                        int value = stack.pop();
                        System.out.println("Thread-" + threadId + " popped: " + value);
                    } catch (EmptyStackException e) {
                        System.out.println("Thread-" + threadId + " found empty stack");
                        // If stack is empty, wait a bit and try again
                        j--; // Don't count this as an operation
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    // Simulate some work
                    try {
                        Thread.sleep((long) (Math.random() * 5));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            consumers[i].start();
        }

        // Wait for all threads to complete
        for (Thread t : producers) {
            t.join();
        }
        for (Thread t : consumers) {
            t.join();
        }

        System.out.println("Demo completed. Stack is empty: " + stack.isEmpty());
    }

}
