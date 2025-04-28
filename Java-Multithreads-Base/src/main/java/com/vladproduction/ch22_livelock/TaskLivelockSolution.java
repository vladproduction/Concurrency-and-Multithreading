package com.vladproduction.ch22_livelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TaskLivelockSolution implements Runnable {

    private static final String TRY_ACQUIRE_LOCK = "Thread '%s' is trying to acquire lock '%s'\n";
    private static final String SUCCESS_ACQUIRE_LOCK = "Thread '%s' acquired lock '%s'\n";
    private static final String RELEASE_LOCK = "Thread '%s' releasing lock '%s'\n";
    private static final String FAILED_ACQUIRE_LOCK = "Thread '%s' failed to acquire lock '%s', will release own lock and retry\n";
    private static final String FIRST_LOCK = "first lock";
    private static final String SECOND_LOCK = "second lock";

    private final Lock firstLock;
    private final Lock secondLock;
    private int attempts = 0;

    public TaskLivelockSolution(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();

        while (!Thread.currentThread().isInterrupted()) {
            attempts++;

            // Try to acquire first lock
            System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);
            firstLock.lock();
            try {
                System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);

                // Sleep to increase chance of livelock
                TimeUnit.MILLISECONDS.sleep(50);

                // Try to acquire second lock
                System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
                boolean acquired = secondLock.tryLock(100, TimeUnit.MILLISECONDS);

                if (acquired) {
                    try {
                        System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);

                        // Successfully acquired both locks, do work here
                        System.out.println(currentThreadName + " has acquired both locks and completed its task!");
                        return; // Exit successfully

                    } finally {
                        secondLock.unlock();
                        System.out.printf(RELEASE_LOCK, currentThreadName, SECOND_LOCK);
                    }
                } else {
                    // Failed to acquire second lock
                    System.out.printf(FAILED_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
                }

            } catch (InterruptedException e) {
                System.out.println(currentThreadName + " was interrupted after " + attempts + " attempts");
                Thread.currentThread().interrupt();
                return;
            } finally {
                firstLock.unlock();
                System.out.printf(RELEASE_LOCK, currentThreadName, FIRST_LOCK);
            }

            // Sleep before retrying
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(currentThreadName + " was interrupted during sleep");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
