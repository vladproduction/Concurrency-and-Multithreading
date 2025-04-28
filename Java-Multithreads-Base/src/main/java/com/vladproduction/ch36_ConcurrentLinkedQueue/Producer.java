package com.vladproduction.ch36_ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Producer thread that adds tasks to the shared queue
 */
public class Producer implements Runnable {

    private final ConcurrentLinkedQueue<Task> queue;
    private final AtomicInteger taskCounter;
    private final int producerId;

    public Producer(ConcurrentLinkedQueue<Task> queue, AtomicInteger taskCounter, int producerId) {
        this.queue = queue;
        this.taskCounter = taskCounter;
        this.producerId = producerId;
    }

    @Override
    public void run() {
        try {
            // Each producer creates 5-10 tasks
            int tasksToCreate = 5 + (int)(Math.random() * 5);

            for (int i = 0; i < tasksToCreate; i++) {
                // Create a new task with a unique ID
                int taskId = taskCounter.incrementAndGet();
                Task task = new Task(taskId, producerId);

                // Add task to the queue
                queue.offer(task);

                System.out.println("Producer-" + producerId + " added " + task +
                        " to queue. Queue size: " + queue.size());

                // Simulate variable production time
                Thread.sleep(200 + (int)(Math.random() * 300));
            }

            System.out.println("Producer-" + producerId + " finished creating tasks.");

        } catch (InterruptedException e) {
            System.err.println("Producer-" + producerId + " interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
