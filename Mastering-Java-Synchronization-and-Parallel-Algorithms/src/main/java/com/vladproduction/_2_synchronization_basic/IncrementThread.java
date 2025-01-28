package com.vladproduction._2_synchronization_basic;

class IncrementThread extends Thread {
    private Counter counter;

    IncrementThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
}
