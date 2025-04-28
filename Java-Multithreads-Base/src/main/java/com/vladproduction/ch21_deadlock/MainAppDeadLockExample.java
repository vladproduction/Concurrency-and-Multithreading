package com.vladproduction.ch21_deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The solution is that deadlocks can be prevented by establishing a consistent global ordering for lock acquisition across all threads.
 * When all threads attempt to acquire multiple locks in the same order, circular wait conditions cannot occur.
 * */
public class MainAppDeadLockExample {
    public static void main(String[] args) {

        Lock firstGivenLock = new ReentrantLock();
        Lock secondGivenLock = new ReentrantLock();

        //problem: deadlock
        //Thread firstGivenThread = new Thread(new TaskDeadlock(firstGivenLock, secondGivenLock));
        //Thread secondGivenThread = new Thread(new TaskDeadlock(secondGivenLock, firstGivenLock));

        //solution: have to use monitors to acquire locks in one order for both threads
        Thread firstGivenThread = new Thread(new TaskDeadlock(firstGivenLock, secondGivenLock));
        Thread secondGivenThread = new Thread(new TaskDeadlock(firstGivenLock, secondGivenLock));

        firstGivenThread.start();
        secondGivenThread.start();

    }
}
