package com.vladproduction.ch03_thread_safety.thread_safe_programming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalVariablesExample {

    // Thread-safe date formatter using ThreadLocal
    private static final ThreadLocal<SimpleDateFormat> dateFormatter = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    // Transaction context example
    private static class TransactionContext {
        private static final ThreadLocal<String> transactionId = new ThreadLocal<>();
        private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
        private static final ThreadLocal<Integer> operationCount = ThreadLocal.withInitial(() -> 0);

        public static void beginTransaction(String txId, User user) {
            transactionId.set(txId);
            currentUser.set(user);
            operationCount.set(0);
        }

        public static void incrementOperations() {
            operationCount.set(operationCount.get() + 1);
        }

        public static String getTransactionInfo() {
            return String.format("Transaction[id=%s, user=%s, operations=%d]",
                    transactionId.get(),
                    currentUser.get(),
                    operationCount.get());
        }

        public static void cleanup() {
            transactionId.remove();
            currentUser.remove();
            operationCount.remove();
        }
    }

    // User class for the example
    private static class User {
        private final String username;

        public User(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return username;
        }
    }

    // Simulated business operation
    private static void processTransaction(String userId) {
        try {
            // Begin transaction
            String txId = "TX-" + System.nanoTime();
            User user = new User(userId);
            TransactionContext.beginTransaction(txId, user);

            // Simulate some work
            for (int i = 0; i < 3; i++) {
                TransactionContext.incrementOperations();
                // Format current timestamp using thread-local formatter
                String timestamp = dateFormatter.get().format(new Date());
                System.out.printf("Thread %s: %s at %s%n",
                        Thread.currentThread().getName(),
                        TransactionContext.getTransactionInfo(),
                        timestamp);
                Thread.sleep(100); // Simulate work
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Clean up thread-local variables
            TransactionContext.cleanup();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit multiple tasks
        String[] users = {"Alice", "Bob", "Charlie", "David", "Eve"};
        for (String user : users) {
            executor.submit(() -> processTransaction(user));
        }

        // Shutdown and wait for completion
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }


}
