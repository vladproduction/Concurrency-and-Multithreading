package com.vladproduction.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentTasksDemo {
    public static void main(String[] args) {
        System.out.println("Main thread started");

        // Create an ExecutorService with a fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // List to hold Future objects
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit multiple tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    if (taskId == 3) {
                        throw new Exception("Exception in task " + taskId); // Simulate exception for task 3
                    }
                    // Simulate some work
                    Thread.sleep(2000);
                    System.out.println("Task " + taskId + " finished");
                    return taskId * 10; // Return some result
                }
            });
            futures.add(future);
        }

        // Process the results
        for (Future<Integer> future : futures) {
            try {
                System.out.println("Result: " + future.get());
            } catch (ExecutionException e) {
                System.out.println("Caught exception: " + e.getCause().getMessage());
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        }

        // Shut down the executor gracefully
        executor.shutdown();
        System.out.println("Main thread finished");
    }
}
