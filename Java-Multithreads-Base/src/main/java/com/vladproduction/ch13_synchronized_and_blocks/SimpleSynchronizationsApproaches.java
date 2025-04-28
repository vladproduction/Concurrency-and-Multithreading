package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.concurrent.TimeUnit;

public class SimpleSynchronizationsApproaches {

    private static int counter;

    public static void main(String[] args) {

        System.out.println("---Synchronized block---");
        synchronizedBlockThis();


        System.out.println("---Synchronized method---");
        synchronizedMethod();


        System.out.println("---Non-synchronized---");
        nonSynchronized();
    }

    //synchronized block :(this){}
    private static void synchronizedBlockThis() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    counter = 0;
                    for (int i = 0; i < 5; i++) {
                        System.out.println(Thread.currentThread().getName() + " : " + counter);
                        counter++;

                    }
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    //synchronized
    private static void synchronizedMethod() {
        Runnable runnable = new Runnable() {

            @Override
            synchronized public void run() {
                counter = 0;
                for (int i = 0; i < 5; i++) {
                    counter += +1;
                    String nameCurrentThread = Thread.currentThread().getName();
                    System.out.println(nameCurrentThread + " : " + counter);

                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

    }

    //non-synchronized
    private static void nonSynchronized() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                counter = 0;
                for (int i = 0; i < 5; i++) {
                    counter += +1;
                    String nameCurrentThread = Thread.currentThread().getName();
                    System.out.println(nameCurrentThread + " : " + counter);

                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

    }
}
