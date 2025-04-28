package com.vladproduction.ch40_ThreadPoolExecutor_Usage.exception_handling;

import java.util.concurrent.*;

public class TestingClass {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Using try-catch inside the task
        executor.submit(() -> {
            try {
                // Task code that might throw an exception
                int result = 10 / 0; // Will throw ArithmeticException
            } catch (Exception e) {
                System.err.println("Task exception caught: " + e.getMessage());
            }
        });

        // Using Future to catch exceptions
        Future<?> future = executor.submit(() -> {
            // Task code that might throw an exception
            int result = 10 / 0; // Will throw ArithmeticException
        });

        try {
            future.get(); // This will rethrow the exception
        } catch (ExecutionException e) {
            System.err.println("Task failed: " + e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Using UncaughtExceptionHandler for thread-level handling
        ThreadFactory factory = r -> {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler((thread, throwable) ->
                    System.err.println("Uncaught exception in " + thread.getName() +
                            ": " + throwable.getMessage()));
            return t;
        };


    }
}
