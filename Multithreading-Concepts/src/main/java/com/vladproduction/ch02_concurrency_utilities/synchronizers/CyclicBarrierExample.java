package com.vladproduction.ch02_concurrency_utilities.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int threadCount = 3;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Create a CyclicBarrier that executes a Runnable when all threads arrive
        CyclicBarrier barrier = new CyclicBarrier(threadCount,
                () -> System.out.println("Phase completed - all threads reached the barrier"));

        for (int i = 0; i < threadCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    // Phase 1
                    System.out.println("Worker " + workerId + " working on phase 1");
                    Thread.sleep(1000 + (workerId * 300));
                    System.out.println("Worker " + workerId + " completed phase 1");
                    barrier.await();

                    // Phase 2 - Barrier resets automatically for reuse
                    System.out.println("Worker " + workerId + " working on phase 2");
                    Thread.sleep(1000 + (workerId * 300));
                    System.out.println("Worker " + workerId + " completed phase 2");
                    barrier.await();

                    System.out.println("Worker " + workerId + " finished all phases");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
