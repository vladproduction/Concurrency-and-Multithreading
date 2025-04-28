package com.vladproduction.ch24_semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * This example demonstrates how to use semaphore with a timeout,
 * which can be useful in scenarios where you want to avoid indefinite waiting.
 *
 * This example shows how to use a semaphore with a timeout.
 * If a thread cannot acquire the semaphore within the specified timeout, it will exit without acquiring the semaphore.
 * This can help prevent threads from waiting indefinitely.
 * */
public class SemaphoreWithTimeoutExample {

    // Create a semaphore with an initial value of 1
    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        // Create three threads
        for (int i = 1; i <= 3; i++) {
            Thread thread = new Thread(new Worker("Thread-" + i));
            thread.start();
        }
    }

    static final class Worker implements Runnable{
        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                // Try to acquire the semaphore with a timeout of 1 second
                if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                    System.out.println(name + " has acquired the semaphore.");

                    // Simulate some work with a sleep
                    System.out.println(name + " is processing...");
                    Thread.sleep(2000);

                    System.out.println(name + " is releasing the semaphore.");
                    // Release the semaphore
                    semaphore.release();
                } else {
                    System.out.println(name + " could not acquire the semaphore and will exit.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
