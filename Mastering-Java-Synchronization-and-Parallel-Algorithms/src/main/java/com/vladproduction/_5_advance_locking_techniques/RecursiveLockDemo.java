package com.vladproduction._5_advance_locking_techniques;

import java.util.concurrent.locks.ReentrantLock;

class RecursiveLock {
    private ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    // Recursive method that increments the count
    public void increment(int level) {
        lock.lock(); // Acquire the lock
        try {
            count++;
            System.out.println("Count at level " + level + ": " + count);
            if (level > 0) {
                increment(level - 1); // Recursive call
            }
        } finally {
            lock.unlock(); // Ensure lock is released
        }
    }

    public int getCount() {
        return count;
    }
}

public class RecursiveLockDemo {
    public static void main(String[] args) {
        RecursiveLock recursiveLock = new RecursiveLock();

        // Start the recursive increment
        recursiveLock.increment(5);

        // Display the final count
        System.out.println("Final count: " + recursiveLock.getCount());
    }
}
