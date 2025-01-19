package com.vladproduction._7_using_anonymous_class;

public class MainLambda {
    public static void main(String[] args) {
        System.out.println("Inside Thread: " + Thread.currentThread().getName());

        Runnable runnable = ()->{
            System.out.println("Inside Thread: " + Thread.currentThread().getName());
        };

        Thread thread = new Thread(runnable);

        System.out.println("Thread start.");
        thread.start();

    }
}
