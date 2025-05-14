package com.vladproduction.thread_safety_and_immutability;

public class ThreadSafeCounter {

    private int counter = 0;

    public synchronized void increment(){
        counter++;
        System.out.println(Thread.currentThread().getName() + " is incrementing counter: " + counter);
    }

    public int getCount(){
        return counter;
    }

}
