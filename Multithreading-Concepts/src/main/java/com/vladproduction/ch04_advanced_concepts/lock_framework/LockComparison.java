package com.vladproduction.ch04_advanced_concepts.lock_framework;

import java.util.concurrent.locks.ReentrantLock;

public class LockComparison {

    // Using synchronized
    private int syncCounter = 0;

    public synchronized void incrementSync() {
        syncCounter++;
    }

    // Using ReentrantLock
    private int lockCounter = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void incrementWithLock() {
        lock.lock();
        try {
            lockCounter++;
        } finally {
            lock.unlock(); // Always release lock in finally block
        }
    }

    // ReentrantLock with tryLock
    public boolean incrementWithTimeout() {
        try {
            // Try to acquire lock for 100 ms
            if (lock.tryLock(100, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                try {
                    lockCounter++;
                    return true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Failed to acquire lock within timeout");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // ReentrantLock with fairness policy
    private final ReentrantLock fairLock = new ReentrantLock(true);

    public void incrementWithFairLock() {
        fairLock.lock();
        try {
            lockCounter++;
        } finally {
            fairLock.unlock();
        }
    }

    // ReentrantLock with lock information
    public void lockInfo() {
        System.out.println("Is locked: " + lock.isLocked());
        System.out.println("Is held by current thread: " + lock.isHeldByCurrentThread());
        System.out.println("Queue length: " + lock.getQueueLength());
    }

}
