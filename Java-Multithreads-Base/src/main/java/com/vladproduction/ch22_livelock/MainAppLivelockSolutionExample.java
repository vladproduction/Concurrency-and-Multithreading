package com.vladproduction.ch22_livelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainAppLivelockSolutionExample {
    public static void main(String[] args) {
        // Create two locks that will be used by the threads
        Lock firstGivenLock = new ReentrantLock();
        Lock secondGivenLock = new ReentrantLock();

        // Create two threads, each trying to acquire the locks in opposite order
        Thread firstThread = new Thread(new TaskLivelockSolution(firstGivenLock, secondGivenLock), "Thread-1");
        Thread secondThread = new Thread(new TaskLivelockSolution(secondGivenLock, firstGivenLock), "Thread-2");

        System.out.println("Starting threads to demonstrate livelock...");

        // Start both threads
        firstThread.start();
        secondThread.start();

        // Monitor for livelock
        new Thread(() -> {
            try {
                // Let the livelock run for a while
                Thread.sleep(5000);
                System.out.println("\n[MONITOR] Livelock detected - both threads are active but not making progress");
                System.out.println("[MONITOR] In a real application, you would implement a strategy to break the livelock");
                System.out.println("[MONITOR] Interrupting threads to terminate demonstration");

                // Interrupt both threads to terminate the demonstration
                firstThread.interrupt();
                secondThread.interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor-Thread").start();
    }
}
