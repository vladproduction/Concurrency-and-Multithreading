package com.vladproduction._3_reentrant_lock;


public class ReentrantLockDemo {
    public static void main(String[] args) {
        LockCounter lockCounter = new LockCounter();

        // Create multiple LockIncrementThreads
        Thread thread1 = new LockIncrementThread(lockCounter);
        Thread thread2 = new LockIncrementThread(lockCounter);
        Thread thread3 = new LockIncrementThread(lockCounter);

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
        System.out.println("Final counter value: " + lockCounter.getCount());
    }
}
