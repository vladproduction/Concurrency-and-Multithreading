package com.vladproduction._7_thread_pool_executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Future<String>> results = new ArrayList<>();

        // Submit tasks to the executor
        for (int i = 1; i <= 5; i++) {
            Task task = new Task("Task " + i);
            Future<String> result = executor.submit(task);
            results.add(result);
        }

        // Shutdown the executor
        executor.shutdown();

        // Process the results
        for (Future<String> future : results) {
            try {
                // Get the result from each Future
                String result = future.get(); // This will block until the result is available
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }
}
