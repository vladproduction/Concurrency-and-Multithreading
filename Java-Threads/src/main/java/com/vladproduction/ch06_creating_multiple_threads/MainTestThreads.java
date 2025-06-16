package com.vladproduction.ch06_creating_multiple_threads;

public class MainTestThreads {
    public static void main(String[] args) {

        new TestThread("TestThread1");
        new TestThread("TestThread2");
        new TestThread("TestThread3");

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            System.out.println("Main thread is Interrupted");
        }

        System.out.println("Main thread is finished.");
    }

}
