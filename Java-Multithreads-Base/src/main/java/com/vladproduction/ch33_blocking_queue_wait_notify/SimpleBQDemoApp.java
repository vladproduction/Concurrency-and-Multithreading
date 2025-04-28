package com.vladproduction.ch33_blocking_queue_wait_notify;

import java.util.concurrent.CountDownLatch;

/**
 * SimpleBQDemoApp - A demonstration of a simple blocking queue implementation
 * This class shows how to create a basic blocking queue with a wait/notify mechanism
 * and proper program termination
 */
public class SimpleBQDemoApp {
    public static void main(String[] args) {

        System.out.println("------ PROGRAM STARTED ------");
        long startTime = System.currentTimeMillis();

        // Number of tasks to create and process
        final int taskCount = 10;

        // Create a latch to wait for all tasks to complete
        CountDownLatch completionLatch = new CountDownLatch(taskCount);

        // Create the queue
        SimpleBlockingQueue<Runnable> taskQueue = new SimpleBlockingQueue<>();

        // Start a worker thread that processes tasks from the queue
        startWorker(taskQueue, completionLatch);

        // Add tasks to the queue
        addTasksToQueue(taskQueue, taskCount, completionLatch);

        try {
            // Wait for all tasks to be processed
            completionLatch.await();

            // Signal the queue that no more items will be added
            taskQueue.shutdown();

            // Allow time for the worker thread to exit gracefully
            Thread.sleep(500);

            long duration = System.currentTimeMillis() - startTime;
            System.out.println("All tasks completed in " + (duration / 1000.0) + " seconds");
            System.out.println("------ PROGRAM FINISHED ------");
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted");
        }

    }

    /**
     * Starts a worker thread that processes tasks from the queue until shutdown
     */
    private static void startWorker(SimpleBlockingQueue<Runnable> queue, CountDownLatch latch) {
        Thread worker = new Thread(() -> {
            System.out.println("Worker thread started");
            while (true) {
                try {
                    // Get a task from the queue (will block if empty)
                    Runnable task = queue.take();

                    // Exit loop if null (indicates shutdown)
                    if (task == null) {
                        System.out.println("Worker thread received shutdown signal");
                        break;
                    }

                    // Execute task
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Worker was interrupted");
                    break;
                }
            }
            System.out.println("Worker thread finished");
        });

        // No need for daemon thread now as we have proper shutdown
        worker.start();
    }

    /**
     * Adds a specified number of sample tasks to the queue
     */
    private static void addTasksToQueue(SimpleBlockingQueue<Runnable> queue,
                                        int taskCount,
                                        CountDownLatch latch) {
        for (int i = 1; i <= taskCount; i++) {  // Fixed: using <= instead of <
            final int taskId = i;
            Runnable task = createTask(taskId, latch);
            System.out.println("Adding task " + taskId + " to queue");
            queue.put(task);
        }
        System.out.println("All tasks have been added to the queue");
    }

    /**
     * Creates a sample task that sleeps for a given time and prints messages
     */
    private static Runnable createTask(int id, CountDownLatch latch) {
        return () -> {
            System.out.println("Task " + id + " STARTED");
            try {
                // Simulate work with random duration between 0.5 and 1.5 seconds
                long workTime = 500 + (int)(Math.random() * 1000);
                Thread.sleep(workTime);
                System.out.println("Task " + id + " processing time: " + (workTime/1000.0) + " seconds");
            } catch (InterruptedException e) {
                System.out.println("Task " + id + " was interrupted");
            }
            System.out.println("Task " + id + " FINISHED");

            // Count down the latch to signal task completion
            latch.countDown();
        };
    }


}
