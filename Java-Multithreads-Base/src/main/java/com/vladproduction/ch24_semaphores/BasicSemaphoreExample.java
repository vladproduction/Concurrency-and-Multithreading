package com.vladproduction.ch24_semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Example demonstrates the simplest use of semaphore to control access to a shared resource.
 * This example shows how to create a semaphore with an initial value of 1,
 * which means only one thread can acquire the semaphore at a time.
 * The threads acquire and release the semaphore to control access to a shared resource.
 * */
public class BasicSemaphoreExample {

    // Semaphore with an initial value of 1
    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Worker("Thread-1"));
        Thread thread2 = new Thread(new Worker("Thread-2"));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
                semaphore.acquire();
                System.out.println(name + " has acquired the semaphore.");
                System.out.println(name + " is processing...");
                TimeUnit.SECONDS.sleep(2); // Simulating processing time.
                System.out.println(name + " is releasing the semaphore.");
                semaphore.release();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

}
