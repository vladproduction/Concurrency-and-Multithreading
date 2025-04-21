package com.vladproduction.ch02_concurrency_utilities.synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        // Only 3 threads can access the resource at the same time
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            final int clientId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Client " + clientId + " is waiting to access the resource");
                    semaphore.acquire();

                    System.out.println("Client " + clientId + " is now using the resource");
                    // Simulate using the resource
                    Thread.sleep(1000);

                    System.out.println("Client " + clientId + " is done with the resource");
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
