package com.vladproduction.ch24_semaphores;

import java.util.concurrent.Semaphore;

/**
 * This example shows how to use semaphore with multiple permits,
 * allowing more than one thread to access the resource concurrently.
 * This example demonstrates how to create a semaphore with an initial value of 2,
 * allowing up to two threads to acquire the semaphore concurrently.
 * This is useful when you want to limit the number of concurrent accesses to a resource but allow more than one.
 * */
public class MultiplePermitsSemaphoreExample {

    // Creating the semaphore with an initial value of 2
    private static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        // Create four threads
        for (int i = 1; i <= 4; i++) {
            Thread thread = new Thread(new Worker("Thread-" + i));
            thread.start();
        }
    }

    static class Worker implements Runnable {
        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                // Acquire the semaphore
                semaphore.acquire();
                System.out.println(name + " has acquired the semaphore.");
                System.out.println(name + " is processing...");
                // Simulate some work with a sleep
                Thread.sleep(2000);
                System.out.println(name + " is releasing the semaphore.");

                // Release the semaphore
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


}
