package com.vladproduction.ch02_concurrency_utilities.executor_framework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Example 2: Future and Callable interfaces
public class FutureCallableExample {

    // Simulates a computationally intensive task
    static class ComplexCalculation implements Callable<Integer> {
        private final int number;
        private final int complexity;

        public ComplexCalculation(int number, int complexity) {
            this.number = number;
            this.complexity = complexity;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() +
                    " calculating for number: " + number);

            // Simulate complex calculation
            int result = 0;
            for (int i = 0; i < complexity * 10; i++) {
                // Simple operation to simulate work
                result += (number * i) % 10;

                // Sleep occasionally to simulate I/O or blocking
                if (i % 100 == 0) {
                    Thread.sleep(10);

                    // Demonstrate cancellation check
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Calculation cancelled!");
                        throw new InterruptedException("Task cancelled");
                    }
                }
            }

            System.out.println(Thread.currentThread().getName() +
                    " completed calculation for: " + number);
            return result;
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();

        System.out.println("Submitting calculation tasks...");

        // Submit tasks and collect futures
        for (int i = 1; i <= 5; i++) {
            // Create task with varying complexity
            Callable<Integer> task = new ComplexCalculation(i, i * 5);

            // Submit task and get future
            Future<Integer> future = executor.submit(task);
            futures.add(future);
        }

        // Demonstrate Future API features
        try {
            // Cancel one of the tasks (if not started yet)
            if (futures.size() > 3) {
                Future<Integer> taskToCancel = futures.get(3);
                boolean cancelled = taskToCancel.cancel(true);
                System.out.println("Task cancellation attempt: " +
                        (cancelled ? "successful" : "failed"));
            }

            // Process results as they complete
            for (int i = 0; i < futures.size(); i++) {
                Future<Integer> future = futures.get(i);

                try {
                    if (!future.isCancelled()) {
                        // Get result with timeout
                        Integer result = future.get(2, TimeUnit.SECONDS);
                        System.out.println("Task " + i +
                                " result: " + result);
                    } else {
                        System.out.println("Task " + i + " was cancelled");
                    }
                } catch (CancellationException e) {
                    System.out.println("Task " + i + " was cancelled");
                } catch (TimeoutException e) {
                    System.out.println("Task " + i +
                            " did not complete in time!");
                    future.cancel(true); // Cancel it
                } catch (ExecutionException e) {
                    System.out.println("Task " + i +
                            " failed with exception: " +
                            e.getCause().getMessage());
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Clean up
            executor.shutdownNow();
        }
    }
}


