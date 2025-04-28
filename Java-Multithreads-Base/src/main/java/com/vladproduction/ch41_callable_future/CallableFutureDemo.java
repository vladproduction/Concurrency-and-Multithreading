package com.vladproduction.ch41_callable_future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This file demonstrates a basic example of using Callable and Future
 * to get results from asynchronous tasks.
 *
 * Key features of Callable and Future:
 *  - Using Callable<T> to define tasks that return results (unlike Runnable)
 *  - Getting results with Future.get() which blocks until the result is ready
 *  - Checking task status with isDone() without blocking
 *  - Handling exceptions thrown during task execution
 *  - Canceling tasks with cancel()
 *
 */
public class CallableFutureDemo {
    public static void main(String[] args) {

        // Create and start our demo
        new CallableFutureDemo().runDemo();

    }

    private void runDemo() {
        System.out.println("Starting Callable/Future Demo");

        // Create task executor with 3 threads
        TaskExecutor executor = new TaskExecutor(3);

        try{
            // Submit multiple calculation tasks with different parameters
            System.out.println("Submitting tasks...");
            Future<Integer> result1 = executor.submitCalculationTask(10, 2000);
            Future<Integer> result2 = executor.submitCalculationTask(20, 1000);
            Future<Integer> result3 = executor.submitCalculationTask(30, 3000);

            // While tasks are executing, we can do other things
            System.out.println("Main thread continues working while tasks execute in background");

            // Check if first result is done (non-blocking call)
            System.out.println("Is first task done? " + result1.isDone());

            // Get results - this will block until each task completes
            System.out.println("Waiting for results...");
            System.out.println("Result 1: " + result1.get());  // Will block until result is available
            System.out.println("Result 2: " + result2.get());  // Will block until result is available
            System.out.println("Result 3: " + result3.get());  // Will block until result is available

            // Submit a task that throws an exception
            System.out.println("\nSubmitting a task that will throw an exception...");
            Future<Integer> failingResult = executor.submitFailingTask();

            try {
                Integer value = failingResult.get();
                System.out.println("Result: " + value);  // We won't reach here
            } catch (ExecutionException e) {
                System.out.println("Task execution failed: " + e.getCause().getMessage());
            }

            // Demonstrate canceling a task
            System.out.println("\nDemonstrating task cancellation...");
            Future<Integer> longRunningTask = executor.submitCalculationTask(50, 10000);

            // Let it run for a bit, then cancel it
            Thread.sleep(1000);
            boolean canceled = longRunningTask.cancel(true);
            System.out.println("Task canceled: " + canceled);
            System.out.println("Is task canceled? " + longRunningTask.isCancelled());

        }
        catch (Exception e) {
            System.err.println("Demo error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            // Always shut down the executor properly
            executor.shutdown();
        }

        System.out.println("Demo completed");
    }
}
