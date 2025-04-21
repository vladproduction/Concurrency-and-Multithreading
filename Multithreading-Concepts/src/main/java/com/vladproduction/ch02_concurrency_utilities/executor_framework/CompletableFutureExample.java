package com.vladproduction.ch02_concurrency_utilities.executor_framework;

import java.util.concurrent.*;

// Example 3: CompletableFuture for composable async operations
public class CompletableFutureExample {

    // Simulate a service that fetches user data
    private static CompletableFuture<String> fetchUserData(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user data for ID: " + userId +
                    " on thread: " + Thread.currentThread().getName());
            simulateNetworkDelay();

            // Return dummy user data
            return "{\"id\":" + userId + ",\"name\":\"User" + userId + "\"}";
        });
    }

    // Simulate a service that fetches user's purchase history
    private static CompletableFuture<String> fetchPurchaseHistory(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching purchase history for ID: " + userId +
                    " on thread: " + Thread.currentThread().getName());
            simulateNetworkDelay();

            // Return dummy purchase data
            return "[{\"itemId\":101,\"date\":\"2023-01-15\"}," +
                    "{\"itemId\":205,\"date\":\"2023-02-20\"}]";
        });
    }

    // Simulate a service that processes recommendations based on user and purchase data
    private static CompletableFuture<String> generateRecommendations(
            String userData, String purchaseHistory) {

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating recommendations on thread: " +
                    Thread.currentThread().getName());
            simulateProcessingDelay();

            // Process the input (in real app, would analyze the JSON data)
            System.out.println("Processing user data: " + userData);
            System.out.println("Processing history: " + purchaseHistory);

            // Return dummy recommendations
            return "[{\"itemId\":301,\"score\":0.9},{\"itemId\":422,\"score\":0.7}]";
        });
    }

    // Simulate a service call that may fail
    private static CompletableFuture<String> unreliableService() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calling unreliable service on thread: " +
                    Thread.currentThread().getName());

            // 50% chance of failure
            if (Math.random() < 0.5) {
                simulateNetworkDelay();
                return "Service data";
            } else {
                throw new RuntimeException("Service unavailable");
            }
        });
    }

    private static void simulateNetworkDelay() {
        try {
            // Simulate network delay
            Thread.sleep((long)(Math.random() * 1000) + 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void simulateProcessingDelay() {
        try {
            // Simulate processing delay
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        // Create a custom executor
        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println("Starting CompletableFuture demo...");

        int userId = 42;

        // 1. Sequential composition with thenApply (transforms result)
        CompletableFuture<Integer> userIdFuture = CompletableFuture
                .supplyAsync(() -> userId, executor)
                .thenApply(id -> {
                    System.out.println("Processing user ID on thread: " +
                            Thread.currentThread().getName());
                    return id + 100; // Transform the value
                });

        try {
            System.out.println("Transformed ID: " + userIdFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 2. Parallel operations with thenCombine
        System.out.println("\nParallel operations with thenCombine:");

        CompletableFuture<String> userDataFuture = fetchUserData(userId);
        CompletableFuture<String> purchaseHistoryFuture = fetchPurchaseHistory(userId);

        // Combine results when both complete
        CompletableFuture<String> recommendationsFuture = userDataFuture
                .thenCombine(purchaseHistoryFuture, (userData, purchases) -> {
                    // Generate recommendations using both results
                    try {
                        return generateRecommendations(userData, purchases).get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new CompletionException(e);
                    }
                });

        try {
            String recommendations = recommendationsFuture.get();
            System.out.println("Final recommendations: " + recommendations);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 3. Error handling with exceptionally and handle
        System.out.println("\nError handling demonstration:");

        // Using exceptionally for fallback
        CompletableFuture<String> reliableFuture = unreliableService()
                .exceptionally(ex -> {
                    System.out.println("Service failed with: " + ex.getMessage());
                    return "Fallback data"; // Provide default value
                });

        try {
            String result = reliableFuture.get();
            System.out.println("Result with fallback: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Using handle for more control
        CompletableFuture<String> handledFuture = unreliableService()
                .handle((result, ex) -> {
                    if (ex != null) {
                        System.out.println("Handling exception: " + ex.getMessage());
                        return "Default data from handler";
                    } else {
                        return "Processed: " + result;
                    }
                });

        try {
            String result = handledFuture.get();
            System.out.println("Result after handling: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 4. Composing multiple futures with allOf/anyOf
        System.out.println("\nComposing multiple futures:");

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            simulateNetworkDelay();
            return "Result from task 1";
        }, executor);

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            simulateNetworkDelay();
            return "Result from task 2";
        }, executor);

        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            simulateNetworkDelay();
            return "Result from task 3";
        }, executor);

        // Wait for all to complete
        CompletableFuture<Void> allTasks = CompletableFuture
                .allOf(task1, task2, task3);

        // thenRun executes an action when the future completes
        allTasks.thenRun(() ->
                System.out.println("All tasks completed!")
        );

        try {
            allTasks.get(5, TimeUnit.SECONDS);

            // Get results from individual futures
            System.out.println("Task1 result: " + task1.get());
            System.out.println("Task2 result: " + task2.get());
            System.out.println("Task3 result: " + task3.get());

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        // Wait for any to complete
        CompletableFuture<Object> anyTask = CompletableFuture
                .anyOf(task1, task2, task3);

        try {
            Object firstResult = anyTask.get();
            System.out.println("First completed result: " + firstResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Clean up the executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("CompletableFuture demo completed");
    }

}
