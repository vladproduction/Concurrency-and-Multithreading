package com.vladproduction.ch04_advanced_concepts.lock_framework;

import java.util.concurrent.locks.StampedLock;

public class PointWithStampedLock {

    private double x, y;
    private final StampedLock lock = new StampedLock();

    // Write lock - exclusive access
    public void move(double deltaX, double deltaY) {
        long stamp = lock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // Read lock - shared access
    public double distanceFromOrigin() {
        long stamp = lock.readLock();
        try {
            return Math.hypot(x, y);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    // Optimistic read - may not need locking at all
    public double optimisticRead() {
        long stamp = lock.tryOptimisticRead();
        double currentX = x;
        double currentY = y;

        // Check if a write occurred after reading values
        if (!lock.validate(stamp)) {
            // Lock was invalidated, fall back to reading with lock
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return Math.hypot(currentX, currentY);
    }

    // Convert read lock to write lock
    public void moveIfAtOrigin(double newX, double newY) {
        long stamp = lock.readLock();
        try {
            // Check if we're at origin
            if (x == 0.0 && y == 0.0) {
                // Try to convert to write lock
                long writeStamp = lock.tryConvertToWriteLock(stamp);
                if (writeStamp != 0L) {
                    // Successfully upgraded to write lock
                    stamp = writeStamp;
                    x = newX;
                    y = newY;
                    return;
                } else {
                    // Could not upgrade, release read lock and acquire write lock
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                    // Check again as another thread might have moved the point
                    if (x == 0.0 && y == 0.0) {
                        x = newX;
                        y = newY;
                    }
                }
            }
        } finally {
            lock.unlock(stamp);
        }
    }

}
