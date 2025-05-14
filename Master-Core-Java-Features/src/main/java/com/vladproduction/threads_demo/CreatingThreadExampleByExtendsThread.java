package com.vladproduction.threads_demo;

public class CreatingThreadExampleByExtendsThread {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        System.out.println("Main thread is running...");
    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread is running...");
        }
    }

}
