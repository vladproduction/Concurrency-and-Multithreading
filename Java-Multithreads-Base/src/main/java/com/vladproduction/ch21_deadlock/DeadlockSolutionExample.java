package com.vladproduction.ch21_deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSolutionExample {

    private static final Lock resourceA = new ReentrantLock();
    private static final Lock resourceB = new ReentrantLock();

    public static void main(String[] args) {
        // Create two threads that acquire locks in different orders
        Thread thread1 = new Thread(() -> {
            try {
                // Thread 1 tries to lock resource A then resource B
                System.out.println("Thread 1: Attempting to lock resource A");
                resourceA.lock();
                System.out.println("Thread 1: Locked resource A");

                // Simulate some work
                TimeUnit.MILLISECONDS.sleep(100);

                System.out.println("Thread 1: Attempting to lock resource B");
                resourceB.lock();
                System.out.println("Thread 1: Locked resource B");

                // Use both resources
                System.out.println("Thread 1: Working with both resources");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // Release locks in reverse order of acquisition
                resourceB.unlock();  // Just unlock without trying to acquire first
                System.out.println("Thread 1: Released resource B");

                resourceA.unlock();  // Just unlock without trying to acquire first
                System.out.println("Thread 1: Released resource A");
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            try {
                // Thread 2 tries to lock resource A then resource B (preventing deadlock)
                System.out.println("Thread 2: Attempting to lock resource A");
                resourceA.lock();
                System.out.println("Thread 2: Locked resource A");

                // Simulate some work
                TimeUnit.MILLISECONDS.sleep(100);

                System.out.println("Thread 2: Attempting to lock resource B");
                resourceB.lock();
                System.out.println("Thread 2: Locked resource B");

                // Use both resources
                System.out.println("Thread 2: Working with both resources");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // Release locks in reverse order of acquisition
                resourceB.unlock();  // Just unlock without trying to acquire first
                System.out.println("Thread 2: Released resource B");

                resourceA.unlock();  // Just unlock without trying to acquire first
                System.out.println("Thread 2: Released resource A");
            }
        }, "Thread-2");

        // Start both threads - this will likely cause a deadlock
        thread1.start();
        thread2.start();
    }

}
