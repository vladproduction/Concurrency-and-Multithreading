package com.vladproduction.ch01_core_concepts.thread_basics;

// Example 2: Thread Lifecycle Demonstration
public class ThreadLifecycleDemo {

    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                // RUNNABLE state
                System.out.println("Thread is running");

                // TIMED_WAITING state
                System.out.println("Thread going to sleep - TIMED_WAITING state");
                Thread.sleep(1000);

                synchronized (lock) {
                    System.out.println("Thread acquired lock");

                    // WAITING state
                    System.out.println("Thread going to wait - WAITING state");
                    lock.wait();

                    System.out.println("Thread woken up and continuing");
                }

            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
            System.out.println("Thread exiting");
        });

        // NEW state
        System.out.println("Thread state before starting: " + thread.getState());

        thread.start();
        Thread.sleep(500); // Give thread time to start

        // Check for TIMED_WAITING
        System.out.println("Thread state during sleep: " + thread.getState());
        Thread.sleep(1000); // Wait for thread to enter wait()

        // Check for WAITING
        System.out.println("Thread state during wait(): " + thread.getState());

        synchronized (lock) {
            // Wake up the thread
            lock.notify();
        }

        thread.join();
        // TERMINATED state
        System.out.println("Thread state after completion: " + thread.getState());
    }

}
