package com.vladproduction.ch36_ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main class demonstrating ConcurrentLinkedQueue usage with multiple producers and consumers
 */
public class ConcurrentQueueDemo {

    public static void main(String[] args) {
        // Create shared concurrent queue
        ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

        // Counter for created and processed tasks
        AtomicInteger tasksCreated = new AtomicInteger(0);
        AtomicInteger tasksProcessed = new AtomicInteger(0);

        // Create thread pools for producers and consumers
        int numProducers = 3;
        int numConsumers = 2;
        ExecutorService producerPool = Executors.newFixedThreadPool(numProducers);
        ExecutorService consumerPool = Executors.newFixedThreadPool(numConsumers);

        System.out.println("Starting ConcurrentLinkedQueue demo with " +
                numProducers + " producers and " +
                numConsumers + " consumers");

        // Start producer threads
        for (int i = 0; i < numProducers; i++) {
            producerPool.submit(new Producer(taskQueue, tasksCreated, i));
        }

        // Start consumer threads
        for (int i = 0; i < numConsumers; i++) {
            consumerPool.submit(new Consumer(taskQueue, tasksProcessed, i));
        }

        // Wait for a while to let the threads work
        try {
            Thread.sleep(5000);

            // Shutdown producer threads first
            System.out.println("\nShutting down producers...");
            producerPool.shutdown();
            producerPool.awaitTermination(2, TimeUnit.SECONDS);

            // Wait for consumers to finish processing remaining tasks
            System.out.println("Waiting for consumers to finish remaining tasks...");
            while (!taskQueue.isEmpty()) {
                Thread.sleep(500);
            }

            // Shutdown consumer threads
            System.out.println("Shutting down consumers...");
            consumerPool.shutdown();
            consumerPool.awaitTermination(2, TimeUnit.SECONDS);

            System.out.println("\nFinal statistics:");
            System.out.println("Total tasks created: " + tasksCreated.get());
            System.out.println("Total tasks processed: " + tasksProcessed.get());
            System.out.println("Tasks remaining in queue: " + taskQueue.size());

        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }

}
