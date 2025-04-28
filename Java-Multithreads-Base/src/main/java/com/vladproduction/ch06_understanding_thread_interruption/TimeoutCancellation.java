package com.vladproduction.ch06_understanding_thread_interruption;

import java.util.concurrent.*;

public class TimeoutCancellation {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create a callable task that might take too long
        Callable<String> longRunningTask = () -> {
            try {
                System.out.println("Task: Starting potentially long operation");
                for (int i = 0; i < 10; i++) {
                    System.out.println("Task: Working... step " + (i + 1));
                    Thread.sleep(1000);

                    // Best practice: Check for interruption even between operations
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Task: Detected interruption, cleaning up");
                        // Do any necessary cleanup here
                        throw new InterruptedException("Task interrupted at step " + i);
                    }
                }
                return "Task completed successfully";
            } catch (InterruptedException e) {
                // Preserve interrupt status
                Thread.currentThread().interrupt();
                System.out.println("Task: Operation was interrupted");
                return "Task was interrupted";
            }
        };

        // Submit the task and get a Future
        Future<String> future = executor.submit(longRunningTask);

        try {
            // Try to get the result with a timeout
            System.out.println("Main: Waiting for result with timeout");
            String result = future.get(3, TimeUnit.SECONDS);
            System.out.println("Main: Got result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Main: Task took too long! Cancelling...");

            // Cancel the task (this interrupts the thread if it's running)
            boolean wasCancelled = future.cancel(true);
            System.out.println("Main: Task cancellation " +
                    (wasCancelled ? "succeeded" : "failed"));
        } catch (InterruptedException e) {
            System.out.println("Main: Waiting was interrupted");
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.println("Main: Task threw an exception: " + e.getCause());
        }

        // Shutdown the executor
        executor.shutdownNow();
        System.out.println("Main: Program completed");
    }
}
