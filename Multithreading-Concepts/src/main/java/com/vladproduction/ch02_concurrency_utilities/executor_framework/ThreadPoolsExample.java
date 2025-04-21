package com.vladproduction.ch02_concurrency_utilities.executor_framework;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// Example 1: ThreadPoolExecutor basics
public class ThreadPoolsExample {

    public static void main(String[] args) {
        // Example task
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Task executing on " + threadName);
            try {
                // Simulate work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 1. Fixed Thread Pool - good for CPU-bound tasks
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        System.out.println("\n--- Fixed Thread Pool (3 threads) ---");
        for (int i = 0; i < 5; i++) {
            fixedPool.submit(task);
        }
        awaitTermination(fixedPool);

        // 2. Cached Thread Pool - good for many short-lived tasks
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        System.out.println("\n--- Cached Thread Pool ---");
        for (int i = 0; i < 10; i++) {
            cachedPool.submit(task);
            // Short delay to demonstrate thread reuse
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        awaitTermination(cachedPool);

        // 3. Single Thread Executor - sequential execution
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        System.out.println("\n--- Single Thread Executor ---");
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            singleThreadPool.submit(() -> {
                System.out.println("Sequential Task " + taskId +
                        " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        awaitTermination(singleThreadPool);

        // 4. Scheduled Thread Pool
        ScheduledExecutorService scheduledPool =
                Executors.newScheduledThreadPool(2);
        System.out.println("\n--- Scheduled Thread Pool ---");

        // Run once after a delay
        scheduledPool.schedule(() ->
                        System.out.println("Delayed task on " +
                                Thread.currentThread().getName()),
                1, TimeUnit.SECONDS);

        // Run periodically
        scheduledPool.scheduleAtFixedRate(() ->
                        System.out.println("Repeating task on " +
                                Thread.currentThread().getName()),
                0, 500, TimeUnit.MILLISECONDS);

        // Let scheduled tasks run for a while
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        awaitTermination(scheduledPool);

        // 5. Custom Thread Pool Configuration
        // Create a pool with custom thread factory and bounded queue
        ThreadFactory customThreadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("CustomPool-Worker-" + counter.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.setDaemon(false); // non-daemon threads
                return thread;
            }
        };

        // Create a custom ThreadPoolExecutor
        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
                2,                          // Core pool size
                4,                          // Max pool size
                60, TimeUnit.SECONDS,       // Keep-alive time for idle threads
                new ArrayBlockingQueue<>(10), // Bounded queue for tasks
                customThreadFactory,        // Custom thread factory
                new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy
        );

        System.out.println("\n--- Custom Thread Pool ---");
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            customPool.submit(() -> {
                System.out.println("Custom Task " + taskId +
                        " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        awaitTermination(customPool);
    }

    private static void awaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            // Wait for tasks to complete
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                // Force shutdown if tasks don't complete in time
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
