package com.vladproduction._3_reentrant_lock;

class LockIncrementThread extends Thread {
    private LockCounter lockCounter;

    LockIncrementThread(LockCounter lockCounter) {
        this.lockCounter = lockCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            lockCounter.increment();
        }
    }
}
