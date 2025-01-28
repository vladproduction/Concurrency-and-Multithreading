package com.vladproduction._2_synchronization_basic;

public class SyncDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // Create multiple IncrementThreads
        Thread thread1 = new IncrementThread(counter);
        Thread thread2 = new IncrementThread(counter);
        Thread thread3 = new IncrementThread(counter);

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // Wait for all threads to finish
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Display the final count
        System.out.println("Final counter value: " + counter.getCount());
    }
}
