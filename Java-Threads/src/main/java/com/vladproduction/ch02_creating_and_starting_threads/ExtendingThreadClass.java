package com.vladproduction.ch02_creating_and_starting_threads;

public class ExtendingThreadClass extends Thread{

    @Override
    public void run() {
        System.out.println("Inside Thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        System.out.println("Inside Thread: " + Thread.currentThread().getName());

        //Creating Thread
        Thread thread = new ExtendingThreadClass();

        System.out.println("Start Thread");
        thread.start();

    }
}
