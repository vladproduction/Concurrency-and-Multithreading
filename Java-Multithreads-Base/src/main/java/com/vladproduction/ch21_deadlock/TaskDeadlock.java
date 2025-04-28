package com.vladproduction.ch21_deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TaskDeadlock implements Runnable{

    private static final String TRY_ACQUIRE_LOCK = "Thread '%s' is trying to acquire lock '%s'\n";
    private static final String SUCCESS_ACQUIRE_LOCK = "Thread '%s' acquired lock '%s'\n";
    private static final String RELEASE_LOCK = "Thread '%s' release lock '%s'\n";
    private static final String FIRST_LOCK = "first lock";
    private static final String SECOND_LOCK = "second lock";

    private final Lock firstLock;
    private final Lock secondLock;

    public TaskDeadlock(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);
        firstLock.lock();
        try {
            System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, FIRST_LOCK);
            TimeUnit.MILLISECONDS.sleep(200);

            System.out.printf(TRY_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
            secondLock.lock();
            try{
                System.out.printf(SUCCESS_ACQUIRE_LOCK, currentThreadName, SECOND_LOCK);
                TimeUnit.MILLISECONDS.sleep(200);
            }finally {
                secondLock.unlock();
                System.out.printf(RELEASE_LOCK, currentThreadName, SECOND_LOCK);
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {
            firstLock.unlock();
            System.out.printf(RELEASE_LOCK, currentThreadName, FIRST_LOCK);
        }


    }

}
