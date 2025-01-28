package com.vladproduction._2_synchronization_basic;

class Counter {
    private int count = 0;

    // Synchronized method to safely increment the count
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
