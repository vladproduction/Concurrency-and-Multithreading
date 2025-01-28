package com.vladproduction._1_intro_concurrency;

public class ThreadDemo {
    public static void main(String[] args) {
        // Create two threads
        SimpleThread thread1 = new SimpleThread("Thread 1");
        SimpleThread thread2 = new SimpleThread("Thread 2");

        // Start the threads
        thread1.start();
        thread2.start();

        try {
            // Wait for threads to finish
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("All threads have finished execution.");
    }
}
