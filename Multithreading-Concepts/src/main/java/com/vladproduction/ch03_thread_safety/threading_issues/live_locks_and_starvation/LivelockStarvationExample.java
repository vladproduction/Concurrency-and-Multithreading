package com.vladproduction.ch03_thread_safety.threading_issues.live_locks_and_starvation;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LivelockStarvationExample {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Livelock Demonstration ===");
        demonstrateLivelock();

        Thread.sleep(1000);
        System.out.println("\n=== Livelock Solution Demonstration ===");
        demonstrateLivelockSolution();

        Thread.sleep(1000);
        System.out.println("\n=== Starvation Demonstration ===");
        demonstrateStarvation();

        Thread.sleep(1000);
        System.out.println("\n=== Starvation Solution Demonstration ===");
        demonstrateStarvationSolution();
    }

    // === Livelock Example ===
    static class ResourceUser {
        private final String name;
        private boolean needsResource = true;

        public ResourceUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean needsResource() {
            return needsResource;
        }

        public void setNeedsResource(boolean needsResource) {
            this.needsResource = needsResource;
        }
    }

    static class SharedResource {
        private ResourceUser owner;

        public SharedResource(ResourceUser owner) {
            this.owner = owner;
        }

        public synchronized ResourceUser getOwner() {
            return owner;
        }

        public synchronized void setOwner(ResourceUser owner) {
            this.owner = owner;
        }
    }

    private static void demonstrateLivelock() {
        final ResourceUser user1 = new ResourceUser("User 1");
        final ResourceUser user2 = new ResourceUser("User 2");
        final SharedResource resource = new SharedResource(user1);

        // User1 thread
        new Thread(() -> {
            while (user1.needsResource()) {
                // Check if other user needs the resource
                if (user2.needsResource()) {
                    System.out.println(user1.getName() + " is politely giving up the resource to " + user2.getName());
                    resource.setOwner(user2);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();

        // User2 thread
        new Thread(() -> {
            while (user2.needsResource()) {
                // Check if other user needs the resource
                if (user1.needsResource()) {
                    System.out.println(user2.getName() + " is politely giving up the resource to " + user1.getName());
                    resource.setOwner(user1);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    // === Livelock Solution ===
    private static void demonstrateLivelockSolution() {
        final ResourceUser user1 = new ResourceUser("User 1");
        final ResourceUser user2 = new ResourceUser("User 2");
        final SharedResource resource = new SharedResource(user1);
        final Random random = new Random();

        // User1 thread with random delay
        new Thread(() -> {
            while (user1.needsResource()) {
                if (user2.needsResource()) {
                    System.out.println(user1.getName() + " is waiting with random delay...");
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    resource.setOwner(user2);
                } else {
                    user1.setNeedsResource(false);
                    System.out.println(user1.getName() + " completed its work.");
                }
            }
        }).start();

        // User2 thread with random delay
        new Thread(() -> {
            while (user2.needsResource()) {
                if (user1.needsResource()) {
                    System.out.println(user2.getName() + " is waiting with random delay...");
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    resource.setOwner(user1);
                } else {
                    user2.setNeedsResource(false);
                    System.out.println(user2.getName() + " completed its work.");
                }
            }
        }).start();
    }

    // === Starvation Example ===
    static class Worker implements Runnable {
        private final Lock lock = new ReentrantLock();
        private final String name;
        private final int priority;
        private final AtomicInteger workCount = new AtomicInteger(0);

        public Worker(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10); // Simulate some work
                    if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
                        try {
                            workCount.incrementAndGet();
                            System.out.println(name + " got the lock. Work count: " + workCount.get());
                            Thread.sleep(100); // Simulate critical section work
                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public int getWorkCount() {
            return workCount.get();
        }
    }

    private static void demonstrateStarvation() throws InterruptedException {
        Worker[] workers = new Worker[3];
        Thread[] threads = new Thread[3];

        // Create workers with different priorities
        for (int i = 0; i < 3; i++) {
            workers[i] = new Worker("Worker " + (i + 1), Thread.MAX_PRIORITY - i);
            threads[i] = new Thread(workers[i]);
            threads[i].setPriority(Thread.MAX_PRIORITY - i);
            threads[i].start();
        }

        // Let them run for a while
        Thread.sleep(2000);

        // Stop threads and show results
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Worker worker : workers) {
            System.out.println(worker.name + " completed " + worker.getWorkCount() + " work items");
        }
    }

    // === Starvation Solution ===
    static class FairWorker implements Runnable {
        private static final ExecutorService executor =
                Executors.newFixedThreadPool(3, new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });

        private final String name;
        private final AtomicInteger workCount = new AtomicInteger(0);

        public FairWorker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Submit work to fair executor
                    Future<?> future = executor.submit(() -> {
                        try {
                            workCount.incrementAndGet();
                            System.out.println(name + " is working. Count: " + workCount.get());
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });

                    future.get(150, TimeUnit.MILLISECONDS);
                    Thread.sleep(10);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public int getWorkCount() {
            return workCount.get();
        }

        public static void shutdown() {
            executor.shutdown();
        }
    }

    private static void demonstrateStarvationSolution() throws InterruptedException {
        FairWorker[] workers = new FairWorker[3];
        Thread[] threads = new Thread[3];

        // Create workers with fair scheduling
        for (int i = 0; i < 3; i++) {
            workers[i] = new FairWorker("Fair Worker " + (i + 1));
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        // Let them run for a while
        Thread.sleep(2000);

        // Stop threads and show results
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (FairWorker worker : workers) {
            System.out.println(worker.name + " completed " + worker.getWorkCount() + " work items");
        }

        FairWorker.shutdown();
    }

}
