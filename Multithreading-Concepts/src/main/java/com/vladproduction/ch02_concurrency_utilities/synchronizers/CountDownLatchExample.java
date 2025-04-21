package com.vladproduction.ch02_concurrency_utilities.synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        // Create a CountDownLatch with a count of 3
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("Starting tasks...");

        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    // Simulate work
                    Thread.sleep(1000 + (taskId * 500));
                    System.out.println("Task " + taskId + " completed");
                    // Decrease the count
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Main thread waits for all tasks to complete
        latch.await();
        System.out.println("All tasks have completed");

        executor.shutdown();
    }

}
