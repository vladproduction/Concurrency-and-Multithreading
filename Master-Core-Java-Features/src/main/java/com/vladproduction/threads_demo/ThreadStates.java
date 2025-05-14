package com.vladproduction.threads_demo;

public class ThreadStates {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        System.out.println("Before start: " + myThread.getState()); //NEW
        myThread.start();
        System.out.println("After start: " + myThread.getState()); //RUNNABLE


    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread is running...");
        }
    }

}
