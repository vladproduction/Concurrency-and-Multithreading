package com.vladproduction.lock_advanced_synchronization.reentrant_lock;

public class TestAppMain {
    public static void main(String[] args) {

        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();

        Thread thread1 = new Thread(reentrantLockExample::increment, "Thread 1");
        Thread thread2 = new Thread(reentrantLockExample::increment, "Thread 2");

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }
}
