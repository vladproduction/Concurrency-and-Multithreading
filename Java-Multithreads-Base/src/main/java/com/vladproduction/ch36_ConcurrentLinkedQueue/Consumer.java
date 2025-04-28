package com.vladproduction.ch36_ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Consumer thread that removes and processes tasks from the shared queue
 */
public class Consumer implements Runnable {

    private final ConcurrentLinkedQueue<Task> queue;
    private final AtomicInteger processedCounter;
    private final int consumerId;

    public Consumer(ConcurrentLinkedQueue<Task> queue, AtomicInteger processedCounter, int consumerId) {
        this.queue = queue;
        this.processedCounter = processedCounter;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        try {
            // Continue processing until interrupted
            while (!Thread.currentThread().isInterrupted()) {
                // Try to get a task from the queue (non-blocking)
                Task task = queue.poll();

                if (task != null) {
                    // Process the task
                    System.out.println("Consumer-" + consumerId + " processing " + task);
                    task.process();

                    // Increment the counter of processed tasks
                    processedCounter.incrementAndGet();

                    System.out.println("Consumer-" + consumerId + " completed " + task +
                            ". Remaining in queue: " + queue.size());
                } else {
                    // If queue is empty, wait a bit before trying again
                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Consumer-" + consumerId + " interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
