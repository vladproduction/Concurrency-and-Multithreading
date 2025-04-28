package com.vladproduction.ch11_threads_factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Demonstrates the SimpleThreadFactory in action by simulating
 * a multi-threaded data processing application.
 */
public class ThreadFactoryDemo {

    public static void main(String[] args) {
        System.out.println("Starting data processing simulation");

        // Create different types of thread factories
        SimpleThreadFactory computeFactory = new SimpleThreadFactory("Compute", Thread.MAX_PRIORITY, false);
        SimpleThreadFactory ioFactory = new SimpleThreadFactory("IO", Thread.NORM_PRIORITY, false);
        SimpleThreadFactory monitorFactory = new SimpleThreadFactory("Monitor", Thread.MIN_PRIORITY, true);

        // Create a latch to wait for main tasks to complete
        int taskCount = 5;
        CountDownLatch completionLatch = new CountDownLatch(taskCount * 2); // For compute and IO tasks

        // Start a monitoring thread
        monitorFactory.newThread(new MonitorTask()).start();

        // Create and start compute-intensive tasks
        List<Thread> computeThreads = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Thread t = computeFactory.newThread(new ComputeTask(i, completionLatch));
            computeThreads.add(t);
            t.start();
        }

        // Create and start IO-bound tasks
        List<Thread> ioThreads = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Thread t = ioFactory.newThread(new IOTask(i, completionLatch));
            ioThreads.add(t);
            t.start();
        }

        // Wait for all tasks to complete
        try {
            System.out.println("Main thread waiting for tasks to complete...");
            completionLatch.await();
            System.out.println("All tasks completed!");
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted while waiting");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simulates a CPU-intensive task like data processing or calculations.
     */
    static class ComputeTask implements Runnable {
        private final int id;
        private final CountDownLatch latch;
        private final Random random = new Random();

        public ComputeTask(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.printf("[%s] Starting compute task %d\n",
                        Thread.currentThread().getName(), id);

                // Simulate complex calculation
                long sum = 0;
                int iterations = 10 + random.nextInt(20);
                for (int i = 0; i < iterations; i++) {
                    // Simulate CPU-intensive work
                    for (int j = 0; j < 10_000_000; j++) {
                        sum += j;
                    }
                    System.out.printf("[%s] Compute progress: %d/%d\n",
                            Thread.currentThread().getName(), i+1, iterations);
                }

                System.out.printf("[%s] Completed compute task %d with result: %d\n",
                        Thread.currentThread().getName(), id, sum % 1000);
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * Simulates an IO-bound task like file or network operations.
     */
    static class IOTask implements Runnable {
        private final int id;
        private final CountDownLatch latch;
        private final Random random = new Random();

        public IOTask(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.printf("[%s] Starting IO task %d\n",
                        Thread.currentThread().getName(), id);

                // Simulate reading data from different sources
                int sources = 3 + random.nextInt(3);
                for (int i = 0; i < sources; i++) {
                    // Simulate IO operation
                    Thread.sleep(300 + random.nextInt(700));
                    System.out.printf("[%s] IO progress: %d/%d\n",
                            Thread.currentThread().getName(), i+1, sources);
                }

                System.out.printf("[%s] Completed IO task %d\n",
                        Thread.currentThread().getName(), id);
            } catch (InterruptedException e) {
                System.out.printf("[%s] IO task %d was interrupted\n",
                        Thread.currentThread().getName(), id);
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * Simulates a background monitoring thread that runs periodically.
     */
    static class MonitorTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.printf("[%s] System monitor started\n",
                        Thread.currentThread().getName());

                // Keep monitoring until program ends
                int count = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(1000);
                    count++;

                    // Print system status
                    System.out.printf("[%s] System check #%d: Active threads: %d\n",
                            Thread.currentThread().getName(),
                            count,
                            Thread.activeCount());

                    // Simulate collecting metrics
                    Runtime runtime = Runtime.getRuntime();
                    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
                    System.out.printf("[%s] Memory usage: %d MB\n",
                            Thread.currentThread().getName(), usedMemory);
                }
            } catch (InterruptedException e) {
                System.out.printf("[%s] Monitor was interrupted\n",
                        Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
    }

}
