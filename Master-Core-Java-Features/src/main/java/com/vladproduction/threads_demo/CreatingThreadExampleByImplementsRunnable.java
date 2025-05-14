package com.vladproduction.threads_demo;

public class CreatingThreadExampleByImplementsRunnable {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        System.out.println("Main thread is running...");
    }

    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("MyRunnable is running...");
        }
    }

}
