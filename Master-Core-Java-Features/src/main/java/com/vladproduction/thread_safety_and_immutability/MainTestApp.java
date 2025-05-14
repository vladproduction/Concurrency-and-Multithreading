package com.vladproduction.thread_safety_and_immutability;

public class MainTestApp {
    public static void main(String[] args) {

        ThreadSafeCounter counter = new ThreadSafeCounter();
        Runnable task = counter::increment;

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");

        t1.start();
        t2.start();
        t3.start();

        try{
            t1.join();
            t2.join();
            t3.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println("Counter: " + counter.getCount());

    }
}
