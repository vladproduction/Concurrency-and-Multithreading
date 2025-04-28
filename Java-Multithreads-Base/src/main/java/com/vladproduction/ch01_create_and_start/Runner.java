package com.vladproduction.ch01_create_and_start;

import static java.lang.Thread.currentThread;

public class Runner {
    public static void main(String[] args) {
        System.out.println(currentThread().getName());

        Thread thread = new MyThread();
        thread.start();

        //anonymous class version#1:
        new Thread(()->System.out.println(currentThread().getName())).start();

        // anonymous class version#2:
        Thread thread1 = new Thread(){
            @Override
            public void run(){
                System.out.println(currentThread().getName());
            }
        };
        thread1.start();

    }

    private static final class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println(currentThread().getName());
        }
    }

}
