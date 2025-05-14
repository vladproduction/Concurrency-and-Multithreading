package com.vladproduction.jvm_and_threads;

public class JVMThreadManagementExample {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new CustomTask(), "Thread-1");
        Thread thread2 = new Thread(new CustomTask(), "Thread-2");
        Thread thread3 = new Thread(new CustomTask(), "Thread-3");

        System.out.println("Thread-1 state before start: " + thread1.getState());
        System.out.println("Thread-2 state before start: " + thread2.getState());
        System.out.println("Thread-3 state before start: " + thread3.getState());

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread-1 state after start: " + thread1.getState());
        System.out.println("Thread-2 state after start: " + thread2.getState());
        System.out.println("Thread-3 state after start: " + thread3.getState());

    }
}
