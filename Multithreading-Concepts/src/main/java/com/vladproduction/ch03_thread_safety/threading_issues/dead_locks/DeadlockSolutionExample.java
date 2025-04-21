package com.vladproduction.ch03_thread_safety.threading_issues.dead_locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSolutionExample {

    private final Lock resource1 = new ReentrantLock();
    private final Lock resource2 = new ReentrantLock();

    // Timeout for lock acquisition
    private static final long LOCK_TIMEOUT = 100;

    public void worker1Function() {
        while (true) {
            try {
                // Try to acquire locks with timeout
                if (acquireLocksWithTimeout("Worker1")) {
                    try {
                        processResources("Worker1");
                    } finally {
                        // Release locks in reverse order
                        resource2.unlock();
                        resource1.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void worker2Function() {
        while (true) {
            try {
                // Try to acquire locks with timeout
                if (acquireLocksWithTimeout("Worker2")) {
                    try {
                        processResources("Worker2");
                    } finally {
                        // Release locks in reverse order
                        resource2.unlock();
                        resource1.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private boolean acquireLocksWithTimeout(String worker) throws InterruptedException {
        System.out.println(worker + ": Attempting to acquire locks...");

        // Try to acquire first lock
        if (!resource1.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
            System.out.println(worker + ": Could not acquire resource1. Backing off...");
            return false;
        }

        // Try to acquire second lock
        if (!resource2.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
            System.out.println(worker + ": Could not acquire resource2. Releasing resource1 and backing off...");
            resource1.unlock();
            return false;
        }

        System.out.println(worker + ": Successfully acquired both locks");
        return true;
    }

    private void processResources(String worker) {
        System.out.println(worker + ": Processing resources...");
        try {
            Thread.sleep(100); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        DeadlockSolutionExample solution = new DeadlockSolutionExample();

        Thread t1 = new Thread(() -> solution.worker1Function(), "Worker1");
        Thread t2 = new Thread(() -> solution.worker2Function(), "Worker2");

        System.out.println("Starting threads - Deadlock is prevented...");
        t1.start();
        t2.start();

        // Let it run for a while
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shutdown gracefully
        t1.interrupt();
        t2.interrupt();
    }


}
