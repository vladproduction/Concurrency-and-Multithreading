package com.vladproduction._3_reentrant_lock;

import java.util.concurrent.locks.ReentrantLock;

class LockCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    // Using ReentrantLock to increment the count
    public void increment() {
        lock.lock(); // Acquire the lock
        try {
            count++;
        } finally {
            lock.unlock(); // Ensure the lock is released
        }
    }

    public int getCount() {
        return count;
    }
}
