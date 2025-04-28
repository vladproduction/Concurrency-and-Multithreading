package com.vladproduction.ch22_livelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * When you run this code, you should see:
 *
 * Both threads continuously trying to acquire locks
 * Each thread successfully acquiring its first lock
 * Each thread failing to acquire the second lock
 * Each thread releasing its first lock
 * This cycle repeating indefinitely (that's the livelock)
 *
 * This creates a true livelock situation where both threads are actively running but never making progress.
 * You might want to add a timeout or some condition to break out of the livelock after a certain number of attempts in a real application.
 * */
public class TaskLivelock implements Runnable{

    private static final String TRY_ACQUIRE_LOCK = "Thread '%s' is trying to acquire lock '%s'\n";
    private static final String SUCCESS_ACQUIRE_LOCK = "Thread '%s' acquired lock '%s'\n";
    private static final String RELEASE_LOCK = "Thread '%s' release lock '%s'\n";
    private static final String FIRST_LOCK = "first lock";
    private static final String SECOND_LOCK = "second lock";

    private final Lock firstLock;
    private final Lock secondLock;

    public TaskLivelock(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();

        while (true) {
            // Try to acquire first lock
            System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);
            firstLock.lock();
            System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);

            try {
                // Sleep a bit before trying the second lock
                TimeUnit.MILLISECONDS.sleep(50);

                // Try to acquire second lock
                System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
                boolean gotSecondLock = secondLock.tryLock();

                if (gotSecondLock) {
                    try {
                        System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
                        // Work is done, break out of the loop
                        System.out.println("Thread '" + currentThreadName + "' completed its task.");
                        break;
                    } finally {
                        secondLock.unlock();
                        System.out.printf(RELEASE_LOCK, currentThreadName, SECOND_LOCK);
                    }
                } else {
                    // Couldn't get second lock, release first and try again
                    System.out.println("Thread '" + currentThreadName + "' couldn't acquire second lock, releasing first lock.");
                }

                // Sleep before next attempt
                TimeUnit.MILLISECONDS.sleep(50);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                firstLock.unlock();
                System.out.printf(RELEASE_LOCK, currentThreadName, FIRST_LOCK);
            }

            // Sleep a bit before next attempt
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
