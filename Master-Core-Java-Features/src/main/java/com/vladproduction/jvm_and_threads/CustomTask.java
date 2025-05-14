package com.vladproduction.jvm_and_threads;

public class CustomTask implements Runnable {

    @Override
    public void run() {
        System.out.println("THREAD:" + Thread.currentThread().getName() + ", STATE: " + Thread.currentThread().getState());
    }
}
