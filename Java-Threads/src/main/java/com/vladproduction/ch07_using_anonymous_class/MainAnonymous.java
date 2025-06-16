package com.vladproduction.ch07_using_anonymous_class;

public class MainAnonymous {
    public static void main(String[] args) {

            System.out.println("Inside Thread: " + Thread.currentThread().getName());

            //Create Runnable as Anonymous class
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Inside Thread: " + Thread.currentThread().getName());
                }
            };

            //Create thread
            Thread thread = new Thread(runnable);

            System.out.println("Start Thread");
            thread.start();

    }
}
