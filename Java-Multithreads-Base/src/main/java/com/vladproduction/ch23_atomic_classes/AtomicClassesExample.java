package com.vladproduction.ch23_atomic_classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This code demonstrates four common atomic classes in Java and their typical use cases in multithreaded applications:
 *
 * AtomicInteger: Used for thread-safe counters that need to be incremented or updated by multiple threads safely without explicit synchronization.
 * AtomicReference: Provides thread-safe operations on object references, useful for implementing lock-free algorithms or when you need to update a reference atomically.
 * AtomicLong: Similar to AtomicInteger but for long values, useful for larger numbers like IDs, timestamps, or statistics in high-throughput applications.
 * AtomicBoolean: Perfect for thread-safe flags, like initialization status, cancellation signals, or any boolean that needs concurrent access.
 *
 * The key advantage of atomic classes is that they use low-level atomic hardware instructions (like Compare-And-Swap operations)
 * instead of locks, making them more efficient for simple operations.
 * They help prevent race conditions while avoiding the overhead and potential deadlocks associated with traditional synchronization mechanisms.
 * */
public class AtomicClassesExample {

    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with 5 threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Example 1: AtomicInteger
        atomicIntegerExample(executorService);

        // Example 2: AtomicReference
        atomicReferenceExample(executorService);

        // Example 3: AtomicLong
        atomicLongExample(executorService);

        // Example 4: AtomicBoolean
        atomicBooleanExample(executorService);

        // Shutdown the executor service
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * Example 1: AtomicInteger
     *
     * Use case: When you need a thread-safe counter.
     * Common applications: Keeping track of counts, IDs, or statistics in concurrent environments.
     */
    private static void atomicIntegerExample(ExecutorService executorService) throws InterruptedException {
        // Regular int (not thread-safe)
        int[] regularCounter = {0};

        // AtomicInteger (thread-safe)
        AtomicInteger atomicCounter = new AtomicInteger(0);

        // Run 1000 increment operations across multiple threads
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                // Increment regular counter (not thread-safe)
                regularCounter[0]++;

                // Increment atomic counter (thread-safe)
                atomicCounter.incrementAndGet();
            });
        }

        // Wait for all tasks to complete
        Thread.sleep(1000);

        System.out.println("Regular counter: " + regularCounter[0]);
        System.out.println("Atomic counter: " + atomicCounter.get());
        // The atomic counter will always be 1000, while the regular counter may be less
        // due to race conditions in multithreaded environments
    }

    /**
     * Example 2: AtomicReference
     *
     * Use case: When you need to atomically update a reference to an object.
     * Common applications: Implementing lock-free data structures, caching values that need atomic updates.
     */
    private static void atomicReferenceExample(ExecutorService executorService) throws InterruptedException {
        class User {
            private final String name;
            private final int age;

            public User(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public String toString() {
                return "User{name='" + name + "', age=" + age + "}";
            }
        }

        // Create an atomic reference initialized with a User object
        AtomicReference<User> userRef = new AtomicReference<>(new User("Initial", 25));

        // Multiple threads trying to update the reference
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            executorService.submit(() -> {
                // Get the current reference
                User oldUser = userRef.get();

                // Create a new user
                User newUser = new User("User" + idx, 25 + idx);

                // Atomically update the reference only if it still points to the expected object
                // This is a "Compare and Set" (CAS) operation
                boolean updated = userRef.compareAndSet(oldUser, newUser);

                System.out.println("Thread " + idx + " update " +
                        (updated ? "succeeded" : "failed") + ": " + newUser);
            });
        }

        Thread.sleep(1000);
        System.out.println("Final user: " + userRef.get());
        // Only one thread will succeed in updating the reference
    }

    /**
     * Example 3: AtomicLong
     *
     * Use case: When you need a thread-safe counter for large numbers.
     * Common applications: High-performance counters, timestamps, IDs in distributed systems.
     */
    private static void atomicLongExample(ExecutorService executorService) throws InterruptedException {
        AtomicLong totalProcessingTime = new AtomicLong(0);

        // Simulate multiple threads updating a shared accumulator
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                // Simulate some processing time
                long processingTime = (long) (Math.random() * 100);

                // Atomically add the processing time to the total
                totalProcessingTime.addAndGet(processingTime);

                // We can also use other atomic operations like:
                // - getAndAdd(value): returns the previous value and adds to it
                // - getAndIncrement(): returns the previous value and increments
                // - getAndSet(newValue): returns the previous value and sets a new one
            });
        }

        Thread.sleep(1000);
        System.out.println("Total processing time: " + totalProcessingTime.get() + "ms");
    }

    /**
     * Example 4: AtomicBoolean
     *
     * Use case: When you need a thread-safe flag that can be toggled.
     * Common applications: One-time initialization flags, status indicators, cancellation signals.
     */
    private static void atomicBooleanExample(ExecutorService executorService) throws InterruptedException {
        AtomicBoolean initialized = new AtomicBoolean(false);

        // Multiple threads might try to initialize a resource
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                // Check if already initialized
                if (initialized.get()) {
                    System.out.println("Thread " + threadId + ": Resource already initialized");
                    return;
                }

                // Try to be the first to initialize
                // compareAndSet will only succeed for one thread
                if (initialized.compareAndSet(false, true)) {
                    System.out.println("Thread " + threadId + ": Initializing resource...");

                    // Simulate initialization work
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println("Thread " + threadId + ": Resource initialization complete");
                } else {
                    System.out.println("Thread " + threadId + ": Another thread is initializing");
                }
            });
        }
    }

}
