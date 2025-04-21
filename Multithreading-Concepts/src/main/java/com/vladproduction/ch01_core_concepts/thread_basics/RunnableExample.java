package com.vladproduction.ch01_core_concepts.thread_basics;

// Example 1: Creating threads using Runnable interface (preferred)
public class RunnableExample {
    public static void main(String[] args) {

        System.out.println("Main thread: " + Thread.currentThread().getName());

        // Creating a thread using Runnable
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello from thread: " + threadName);

            // Simulate work
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println(threadName + " - Count: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted!");
                // Important: Reset the interrupt flag
                Thread.currentThread().interrupt();
            }

            System.out.println(threadName + " finished execution");
        };

        // Start multiple threads with the same task
        Thread thread1 = new Thread(task, "WorkerThread-1");
        Thread thread2 = new Thread(task, "WorkerThread-2");

        thread1.start(); // Don't call run() directly!
        thread2.start();

        // Main thread continues execution
        System.out.println("Main thread continues...");

        try {
            // Wait for both threads to finish
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished. Main thread exiting.");

    }
}
