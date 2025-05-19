package com.vladproduction.ch0_training.threads_practice;

import java.util.concurrent.TimeUnit;

public class CreatingThreadsExample {
    public static void main(String[] args) {



        Thread thread1 = new Thread(()-> System.out.println("Logic of thread 1 is printed"));
        thread1.setName("Thread-1");
        thread1.run();
        thread1.start();

        String name = Thread.currentThread().getName();
        System.out.println(name + " is Started");

        Thread thread2 = new Thread(()-> {

            try {
                System.out.println("Thread started..." + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(5000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread finished!" + Thread.currentThread().getName());

        });

        thread2.setName("Thread-2");
        thread2.start();

        Thread thread3 = new Thread(()-> {

            try {
                System.out.println("Thread started..." + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(10_000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread finished!" + Thread.currentThread().getName());

        });

        thread3.setName("Thread-3");
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main Finished!");
    }
}
