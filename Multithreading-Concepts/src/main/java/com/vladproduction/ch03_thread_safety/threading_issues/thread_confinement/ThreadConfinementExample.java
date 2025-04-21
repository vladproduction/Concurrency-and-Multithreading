package com.vladproduction.ch03_thread_safety.threading_issues.thread_confinement;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadConfinementExample {

    public static void main(String[] args) throws InterruptedException {
        // Demonstrate different thread confinement techniques
        stackConfinementExample();
        threadLocalConfinementExample();
        threadPoolConfinementExample();
    }

    // === 1. Stack Confinement Example ===
    static class StackConfinement {
        public void processUserData(String userId) {
            // Local variables are naturally thread-confined
            List<String> userHistory = new ArrayList<>();
            Map<String, Integer> userStats = new HashMap<>();

            // Perform operations using thread-confined objects
            userHistory.add("Login: " + System.currentTimeMillis());
            userStats.put("loginCount", 1);

            // Process the data
            processHistory(userHistory, userStats);
        }

        private void processHistory(List<String> history, Map<String, Integer> stats) {
            System.out.println("Processing user history: " + history);
            System.out.println("User stats: " + stats);
        }
    }

    // === 2. ThreadLocal Confinement Example ===
    static class ThreadLocalConfinement {
        // ThreadLocal for date formatter (common use case)
        private static final ThreadLocal<SimpleDateFormat> dateFormatter =
                ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // ThreadLocal for user context
        private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

        static class UserContext {
            private final String userId;
            private final List<String> userActivities;

            public UserContext(String userId) {
                this.userId = userId;
                this.userActivities = new ArrayList<>();
            }

            public void addActivity(String activity) {
                userActivities.add(dateFormatter.get().format(new Date()) + " - " + activity);
            }

            public List<String> getActivities() {
                return new ArrayList<>(userActivities);
            }
        }

        public void processUserRequest(String userId, String activity) {
            try {
                // Initialize user context for this thread
                userContext.set(new UserContext(userId));

                // Add activity
                userContext.get().addActivity(activity);

                // Process activities
                System.out.println("Thread " + Thread.currentThread().getName() +
                        " activities: " + userContext.get().getActivities());
            } finally {
                // Important: Clean up ThreadLocal to prevent memory leaks
                userContext.remove();
            }
        }
    }

    // === 3. Thread Pool Confinement Example ===
    static class ThreadPoolConfinement {
        private static class DataProcessor {
            private final Queue<String> dataQueue = new ConcurrentLinkedQueue<>();
            private final Map<String, Integer> processedData = new HashMap<>();

            public void addData(String data) {
                dataQueue.offer(data);
            }

            public void processData() {
                String data;
                while ((data = dataQueue.poll()) != null) {
                    // Process data in thread-confined manner
                    processedData.put(data, data.length());
                }
                System.out.println("Thread " + Thread.currentThread().getName() +
                        " processed: " + processedData);
            }
        }

        private final ExecutorService executor;
        private final Map<Integer, DataProcessor> processorMap = new ConcurrentHashMap<>();

        public ThreadPoolConfinement(int threads) {
            this.executor = Executors.newFixedThreadPool(threads);
            // Initialize processors for each thread
            for (int i = 0; i < threads; i++) {
                processorMap.put(i, new DataProcessor());
            }
        }

        public void processData(String data, int processorId) {
            DataProcessor processor = processorMap.get(processorId);
            if (processor != null) {
                processor.addData(data);
                executor.execute(processor::processData);
            }
        }

        public void shutdown() {
            executor.shutdown();
        }
    }

    // === Demonstration Methods ===
    private static void stackConfinementExample() {
        System.out.println("\n=== Stack Confinement Example ===");
        StackConfinement sc = new StackConfinement();

        // Multiple threads using the same method, but with thread-safe local variables
        for (int i = 0; i < 3; i++) {
            final int userId = i;
            new Thread(() -> sc.processUserData("user" + userId)).start();
        }
    }

    private static void threadLocalConfinementExample() throws InterruptedException {
        System.out.println("\n=== ThreadLocal Confinement Example ===");
        ThreadLocalConfinement tlc = new ThreadLocalConfinement();

        // Create multiple threads accessing the same methods
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int userId = i;
            Thread t = new Thread(() -> {
                tlc.processUserRequest("user" + userId, "login");
                tlc.processUserRequest("user" + userId, "view_profile");
            });
            threads.add(t);
            t.start();
        }

        // Wait for all threads to complete
        for (Thread t : threads) {
            t.join();
        }
    }

    private static void threadPoolConfinementExample() throws InterruptedException {
        System.out.println("\n=== Thread Pool Confinement Example ===");
        ThreadPoolConfinement tpc = new ThreadPoolConfinement(3);

        // Submit multiple tasks
        for (int i = 0; i < 9; i++) {
            final String data = "Data-" + i;
            tpc.processData(data, i % 3);
        }

        // Allow some time for processing
        Thread.sleep(1000);
        tpc.shutdown();
    }


}
