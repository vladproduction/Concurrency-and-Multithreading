package com.vladproduction.concepts_of_threading_in_Java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Approach #4: Simplicity for Developers
 * Concept:
 * Using threads simplifies programming because they share data and provide easy-to-use
 * APIs for synchronization and communication among threads.
 *
 * Code Example: Producer-Consumer Scenario
 * This program demonstrates the producer-consumer problem using threads:
 * Producer: A thread that generates data (items) and adds it to a shared queue until the queue reaches its defined limit.
 * Consumer: A thread that removes data from the queue when it is not empty.
 * Both threads use synchronization to access the shared queue safely.
 * They use wait() to pause execution when the queue is full or empty and notifyAll() to wake up the waiting threads when items are produced or consumed.
 * This example shows how simple it is to create a cooperative threading mechanism using Java's built-in threading primitives.
 * */
public class ProducerConsumerExample {
    private static final Queue<Integer> queue = new LinkedList<>();
    private static final int LIMIT = 5; // Limit for the queue

    public static void main(String[] args) {
        // Create producer and consumer threads
        Thread producer = new Thread(new Producer(), "Producer-Thread");
        Thread consumer = new Thread(new Consumer(), "Consumer-Thread");

        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    // Produce an item
                    while (queue.size() == LIMIT) {
                        try {
                            queue.wait(); // Wait for space to become available
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    int item = (int) (Math.random() * 100);
                    queue.add(item);
                    System.out.println(Thread.currentThread().getName() + " produced: " + item);
                    queue.notifyAll(); // Notify the consumer that an item is available
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait(); // Wait for an item to consume
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    int item = queue.poll();
                    System.out.println(Thread.currentThread().getName() + " consumed: " + item);
                    queue.notifyAll(); // Notify the producer that space is available
                }
            }
        }
    }
}
