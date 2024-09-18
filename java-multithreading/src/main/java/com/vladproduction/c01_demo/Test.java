package com.vladproduction.c01_demo;


public class Test {
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Main is running!");

    }

    static class MyThread extends Thread{

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("myThread is running: " + i);
            }
        }
    }
}
