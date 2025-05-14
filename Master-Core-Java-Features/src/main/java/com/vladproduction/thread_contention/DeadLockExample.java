package com.vladproduction.thread_contention;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {

        Runnable task1 = () -> {
            lock1.lock();
            try{
                System.out.println(Thread.currentThread().getName() + " is acquired lock1. Waiting for lock 2.");
                TimeUnit.SECONDS.sleep(1);
                lock2.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + " is acquired lock2.");
                }finally {
                    lock2.unlock();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }finally {
                lock1.unlock();
            }
        };

        Runnable task2 = () -> {
            lock2.lock();
            try{
                System.out.println(Thread.currentThread().getName() + " is acquired lock2. Waiting for lock 1.");
                TimeUnit.SECONDS.sleep(1);
                lock1.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + " is acquired lock1.");
                }finally {
                    lock1.unlock();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }finally {
                lock2.unlock();
            }
        };

        Thread t1 = new Thread(task1, "Thread 1");
        Thread t2 = new Thread(task2, "Thread 2");

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }
}
