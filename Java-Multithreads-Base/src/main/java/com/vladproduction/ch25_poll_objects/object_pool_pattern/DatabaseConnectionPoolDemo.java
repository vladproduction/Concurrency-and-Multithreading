package com.vladproduction.ch25_poll_objects.object_pool_pattern;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A demo application showing a thread-safe object pool implementation
 */
public class DatabaseConnectionPoolDemo {

    public static void main(String[] args) {
        // Create a pool with 5 database connections
        DatabaseConnectionPool connectionPool = new DatabaseConnectionPool(5);

        // Create a thread pool with 10 worker threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        System.out.println("Starting client threads to use connections from the pool...");

        // Submit 20 tasks that will compete for the 5 connections
        for (int i = 1; i <= 20; i++) {
            final int clientId = i;
            executorService.submit(() -> {
                try {
                    // Get a connection from the pool (will block if none available)
                    DatabaseConnection connection = connectionPool.borrowConnection();
                    System.out.println("Client " + clientId + " acquired " + connection);

                    // Simulate doing some database work
                    Thread.sleep((long) (Math.random() * 1000) + 1000);
                    System.out.println("Client " + clientId + " finished using " + connection);

                    // Return the connection to the pool
                    connectionPool.returnConnection(connection);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Client " + clientId + " was interrupted");
                }
            });
        }

        // Shutdown executor service gracefully
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            System.out.println("All tasks completed. Pool statistics:");
            System.out.println("Total connections created: " + connectionPool.getTotalConnectionsCreated());
            System.out.println("Current pool size: " + connectionPool.getCurrentPoolSize());
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Represents a database connection that can be pooled
     */
    static class DatabaseConnection {
        private final int id;
        private final AtomicInteger usageCount = new AtomicInteger(0);

        public DatabaseConnection(int id) {
            this.id = id;
            // Simulate the overhead of creating a database connection
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void incrementUsage() {
            usageCount.incrementAndGet();
        }

        public int getUsageCount() {
            return usageCount.get();
        }

        @Override
        public String toString() {
            return "Connection-" + id + " (used " + usageCount.get() + " times)";
        }
    }

    /**
     * A thread-safe pool of database connections
     */
    static class DatabaseConnectionPool {
        private final BlockingQueue<DatabaseConnection> pool;
        private final int maxSize;
        private final AtomicInteger connectionCounter = new AtomicInteger(0);

        public DatabaseConnectionPool(int maxSize) {
            this.maxSize = maxSize;
            this.pool = new LinkedBlockingQueue<>(maxSize);

            // Pre-populate the pool with connections
            for (int i = 0; i < maxSize; i++) {
                DatabaseConnection connection = createConnection();
                pool.offer(connection);
            }

            System.out.println("Pool initialized with " + maxSize + " connections");
        }

        /**
         * Creates a new database connection
         */
        private DatabaseConnection createConnection() {
            int id = connectionCounter.incrementAndGet();
            System.out.println("Creating new connection: Connection-" + id);
            return new DatabaseConnection(id);
        }

        /**
         * Borrows a connection from the pool, blocking if none available
         */
        public DatabaseConnection borrowConnection() throws InterruptedException {
            System.out.println("Waiting for an available connection... (" +
                    pool.size() + "/" + maxSize + " available)");
            DatabaseConnection connection = pool.take(); // Blocks if pool is empty
            connection.incrementUsage();
            return connection;
        }

        /**
         * Returns a connection to the pool
         */
        public void returnConnection(DatabaseConnection connection) {
            System.out.println("Returning " + connection + " to the pool");
            pool.offer(connection);
        }

        /**
         * Returns the total number of connections created
         */
        public int getTotalConnectionsCreated() {
            return connectionCounter.get();
        }

        /**
         * Returns the current size of the pool
         */
        public int getCurrentPoolSize() {
            return pool.size();
        }
    }

}
