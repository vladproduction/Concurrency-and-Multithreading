package com.vladproduction.ch01_core_concepts.thread_synchronization;

// Example 2: Class-level Locking with Static Synchronized Methods
public class Counter {

    // Shared among all instances of Counter
    private static int sharedCounter = 0;

    // Instance-specific counter
    private int instanceCounter = 0;

    // Class-level synchronization (locks the Class object)
    public static synchronized void incrementSharedCounter() {
        sharedCounter++;
        System.out.println(Thread.currentThread().getName() +
                " - Shared counter: " + sharedCounter);
    }

    // Alternative way to do class-level locking
    public static void decrementSharedCounter() {
        synchronized (Counter.class) {
            sharedCounter--;
            System.out.println(Thread.currentThread().getName() +
                    " - Shared counter: " + sharedCounter);
        }
    }

    // Instance-level synchronization (locks the instance object)
    public synchronized void incrementInstanceCounter() {
        instanceCounter++;
        System.out.println(Thread.currentThread().getName() +
                " - Instance counter: " + instanceCounter);
    }

    // For demonstration
    public static int getSharedCounter() {
        return sharedCounter;
    }

    public int getInstanceCounter() {
        return instanceCounter;
    }

    public static void main(String[] args) throws InterruptedException {
        // Create two counter instances
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        // Thread working with counter1
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                counter1.incrementInstanceCounter(); // Object lock
                Counter.incrementSharedCounter();    // Class lock
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-1");

        // Thread working with counter2
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                counter2.incrementInstanceCounter(); // Different object lock
                Counter.incrementSharedCounter();    // Same class lock
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nFinal Results:");
        System.out.println("counter1 instance value: " + counter1.getInstanceCounter());
        System.out.println("counter2 instance value: " + counter2.getInstanceCounter());
        System.out.println("Shared counter value: " + Counter.getSharedCounter());
    }

}
