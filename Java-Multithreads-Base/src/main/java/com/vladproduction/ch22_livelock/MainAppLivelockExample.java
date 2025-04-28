package com.vladproduction.ch22_livelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainAppLivelockExample {
    public static void main(String[] args) {

        Lock firstGivenLock = new ReentrantLock();
        Lock secondGivenLock = new ReentrantLock();

        Thread firstThread = new Thread(new TaskLivelock(firstGivenLock, secondGivenLock));
        Thread secondThread = new Thread(new TaskLivelock(secondGivenLock, firstGivenLock));

        firstThread.start();
        secondThread.start();

    }
}
