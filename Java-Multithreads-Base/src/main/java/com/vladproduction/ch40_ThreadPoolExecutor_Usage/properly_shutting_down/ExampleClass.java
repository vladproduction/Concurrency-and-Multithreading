package com.vladproduction.ch40_ThreadPoolExecutor_Usage.properly_shutting_down;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExampleClass {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks...

// Initiate orderly shutdown - allows previously submitted tasks to complete
        executor.shutdown();

// Wait for all tasks to complete or timeout occurs
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // Force shutdown if tasks don't complete in time
                executor.shutdownNow();

                // Wait again, giving tasks the chance to respond to interruption
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


    }
}
