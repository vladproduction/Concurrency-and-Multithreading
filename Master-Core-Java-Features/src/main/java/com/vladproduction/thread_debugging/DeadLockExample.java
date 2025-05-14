package com.vladproduction.thread_debugging;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {

        Runnable task1 = () -> {
            try{
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + ": acquired Lock 1");
                TimeUnit.MILLISECONDS.sleep(500L);

                lock2.lock();
                System.out.println(Thread.currentThread().getName() + ": acquired Lock 2");
                TimeUnit.MILLISECONDS.sleep(500L);

                lock2.unlock();
                TimeUnit.MILLISECONDS.sleep(500L);
                lock1.unlock();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        };

        Runnable task2 = () -> {
            try{
                //lock2.lock(); //deadlock occur
                lock1.lock(); //deadlock solution (change lock same as first in task1)
                System.out.println(Thread.currentThread().getName() + ": acquired Lock 1"); //printing correct here as well (now it correct version with '1')
                TimeUnit.MILLISECONDS.sleep(500L);

                //lock1.lock(); //deadlock occur
                lock2.lock(); //same in here we have to change from 1 to 2
                System.out.println(Thread.currentThread().getName() + ": acquired Lock 2"); //printing correct here as well (now it correct version with '2')
                TimeUnit.MILLISECONDS.sleep(500L);

                lock2.unlock(); //need to change unlocking as well from 1 to 2
                TimeUnit.MILLISECONDS.sleep(500L);
                lock1.unlock();//need to change unlocking as well from 2 to 1
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(task1, "Thread1");
        Thread t2 = new Thread(task2, "Thread2");

        t1.start();
        t2.start();

    }

}
