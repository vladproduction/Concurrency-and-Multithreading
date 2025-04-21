package com.vladproduction.ch04_advanced_concepts.memory_model;

public class VolatileExample {

    // Using volatile ensures memory visibility across threads
    private static volatile boolean stopRequested = false;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            // Will terminate because volatile ensures the background thread
            // will see the updated value
            while (!stopRequested) {
                i++;
            }
            System.out.println("Background thread stopped after " + i + " iterations");
        });

        backgroundThread.start();

        Thread.sleep(1000);
        stopRequested = true; // Visible to background thread due to volatile

        System.out.println("Main thread set stopRequested to true");
    }

    // Non-atomic operations example
    private static volatile int counter = 0;

    public static void incrementCounter() {
        counter++; // Not atomic even with volatile!
    }

    // For atomic operations, you'd need AtomicInteger
    private static java.util.concurrent.atomic.AtomicInteger atomicCounter =
            new java.util.concurrent.atomic.AtomicInteger(0);

    public static void incrementAtomicCounter() {
        atomicCounter.incrementAndGet(); // Atomic operation
    }

    // Volatile array - elements aren't volatile, only the reference is
    private static volatile String[] messages = new String[10];

    public static void updateMessage(int index, String message) {
        messages[index] = message; // Individual elements aren't volatile
    }

}
