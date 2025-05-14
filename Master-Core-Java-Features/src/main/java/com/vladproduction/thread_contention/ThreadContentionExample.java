package com.vladproduction.thread_contention;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadContentionExample {

    private static final Lock lock = new ReentrantLock();//prevent multiple threads from accessing the same resource concurrently.
    private static int counter = 0;


    public static void main(String[] args) {

        Runnable task = () -> {
            for (int i = 1; i <= 3; i++) {
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + " is incrementing counter.");
                    counter++;
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }finally {
                    System.out.println(Thread.currentThread().getName() + " is releasing lock.");
                    lock.unlock();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");

        t1.start();
        t2.start();
        t3.start();



    }
}
