package com.vladproduction.ch06_understanding_thread_interruption;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceInterruption {
    public static void main(String[] args) throws InterruptedException{

        // Create a thread pool with custom thread factory that names our threads
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "Worker-" + counter.getAndIncrement());
                return thread;
            }
        };

        // Create an executor with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3, threadFactory);

        try {
            // Submit several tasks
            for (int i = 0; i < 5; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    try {
                        runTask(taskId);
                        return "Task " + taskId + " completed";
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() +
                                ": Task " + taskId + " was interrupted");
                        return "Task " + taskId + " interrupted";
                    }
                });
            }
            // Let tasks run for a bit
            Thread.sleep(3000);

            // Demonstrate shutdown (sends interrupts to all tasks)
            System.out.println("Main: Initiating shutdown");
            executor.shutdown();

            // Wait a bit to see if tasks complete
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Main: Some tasks didn't finish in time");

                // Force shutdown with shutdownNow (more aggressive interruption)
                System.out.println("Main: Forcing shutdown with shutdownNow()");
                executor.shutdownNow();

                // Wait again
                if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                    System.out.println("Main: Executor still didn't terminate");
                }
            }
        } finally {
            // Always good practice to ensure shutdown
            if (!executor.isShutdown()) {
                executor.shutdownNow();
            }
            System.out.println("Main: Program completed");
        }
    }

    private static void runTask(int id) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": Starting task " + id);
        int steps = 10;

        for (int i = 0; i < steps; i++) {
            // Good practice: Check for interruption regularly
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Task " + id + " interrupted at step " + i);
            }

            System.out.println(Thread.currentThread().getName() +
                    ": Task " + id + " step " + (i + 1) + "/" + steps);
            Thread.sleep(1000); // This can throw InterruptedException
        }

        System.out.println(Thread.currentThread().getName() + ": Task " + id + " completed");
    }

}
