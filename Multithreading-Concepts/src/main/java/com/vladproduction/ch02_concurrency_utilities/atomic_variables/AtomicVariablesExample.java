package com.vladproduction.ch02_concurrency_utilities.atomic_variables;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicVariablesExample {

    // Regular counter - not thread-safe
    static class UnsafeCounter {
        private volatile int value = 0; // volatile to prevent exception

        public void increment() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }

    // Thread-safe counter using AtomicInteger
    static class AtomicCounter {
        private final AtomicInteger value = new AtomicInteger(0);

        public void increment() {
            value.incrementAndGet(); // Atomic operation
        }

        public int getValue() {
            return value.get();
        }
    }

    // Complex object for AtomicReference demo
    static class User {
        private final String name;
        private final int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }

        // Creates a new User with updated age
        public User withAge(int newAge) {
            return new User(this.name, newAge);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numThreads = 10;
        int incrementsPerThread = 1000;

        // 1. Compare unsynchronized vs atomic counter
        demonstrateAtomicInteger(numThreads, incrementsPerThread);

        // 2. AtomicReference for updating reference types
        demonstrateAtomicReference();

        // 3. AtomicIntegerArray for array elements
        demonstrateAtomicArray(numThreads);

        // 4. AtomicFieldUpdater for existing classes
        demonstrateFieldUpdater(numThreads, incrementsPerThread);
    }

    private static void demonstrateAtomicInteger(int numThreads, int incrementsPerThread)
            throws InterruptedException {

        System.out.println("=== AtomicInteger Demonstration ===");

        // Create the counters
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        AtomicCounter atomicCounter = new AtomicCounter();

        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads * 2);

        // Submit tasks that increment the unsafe counter
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    unsafeCounter.increment();
                }
                latch.countDown();
            });
        }

        // Submit tasks that increment the atomic counter
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    atomicCounter.increment();
                }
                latch.countDown();
            });
        }

        // Wait for all tasks to complete
        latch.await();

        // Check results
        System.out.println("Expected count: " + (numThreads * incrementsPerThread));
        System.out.println("Unsafe counter: " + unsafeCounter.getValue() +
                " (likely shows race condition)");
        System.out.println("Atomic counter: " + atomicCounter.getValue());

        // Demonstrate more AtomicInteger operations
        AtomicInteger counter = new AtomicInteger(10);
        System.out.println("\nAtomicInteger operations:");
        System.out.println("Initial: " + counter.get());
        System.out.println("getAndIncrement(): " + counter.getAndIncrement() +
                ", after: " + counter.get());
        System.out.println("addAndGet(5): " + counter.addAndGet(5));

        // Compare and set
        boolean success = counter.compareAndSet(16, 20);
        System.out.println("compareAndSet(16, 20): " + success +
                ", new value: " + counter.get());

        // This will fail as current value is now 20, not 16
        success = counter.compareAndSet(16, 30);
        System.out.println("compareAndSet(16, 30): " + success +
                ", value unchanged: " + counter.get());

        // UpdateAndGet with lambda
        int result = counter.updateAndGet(x -> x * 2);
        System.out.println("updateAndGet(x -> x * 2): " + result);

        executor.shutdown();
    }

    private static void demonstrateAtomicReference() {
        System.out.println("\n=== AtomicReference Demonstration ===");

        // Create an AtomicReference with initial User
        AtomicReference<User> userRef = new AtomicReference<>(
                new User("John", 25)
        );

        System.out.println("Initial user: " + userRef.get());

        // Multiple threads might try to update the User's age
        // Atomic update with getAndUpdate
        User oldUser = userRef.getAndUpdate(user -> user.withAge(user.getAge() + 1));

        System.out.println("Old user: " + oldUser);
        System.out.println("New user: " + userRef.get());

        // Compare and set with explicit CAS loop
        boolean updated = false;
        while (!updated) {
            User currentUser = userRef.get();
            User newUser = new User(currentUser.getName(), 30); // Set age to 30
            updated = userRef.compareAndSet(currentUser, newUser);
        }
        System.out.println("After CAS update: " + userRef.get());
    }

    private static void demonstrateAtomicArray(int numThreads) throws InterruptedException {
        System.out.println("\n=== AtomicIntegerArray Demonstration ===");

        java.util.concurrent.atomic.AtomicIntegerArray array =
                new java.util.concurrent.atomic.AtomicIntegerArray(10);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        // Have multiple threads increment different array elements
        for (int i = 0; i < numThreads; i++) {
            final int index = i % array.length();
            executor.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    array.incrementAndGet(index);
                }
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("Array elements after concurrent updates:");
        for (int i = 0; i < array.length(); i++) {
            System.out.printf("array[%d] = %d%n", i, array.get(i));
        }

        executor.shutdown();
    }

    private static void demonstrateFieldUpdater(int numThreads, int incrementsPerThread)
            throws InterruptedException {
        System.out.println("\n=== AtomicFieldUpdater Demonstration ===");

        // Create an atomic updater for the 'value' field in UnsafeCounter
        java.util.concurrent.atomic.AtomicIntegerFieldUpdater<UnsafeCounter> updater =
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater(
                        UnsafeCounter.class, "value");

        UnsafeCounter counter = new UnsafeCounter();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        // Use field updater to safely increment the counter
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    updater.incrementAndGet(counter);
                }
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("Expected count: " + (numThreads * incrementsPerThread));
        System.out.println("Actual count using field updater: " + counter.getValue());

        executor.shutdown();
    }


}
