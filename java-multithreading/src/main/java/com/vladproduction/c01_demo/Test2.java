package com.vladproduction.c01_demo;

public class Test2 {

    public static void main(String[] args) {
        Test.MyThread myThread1 = new Test.MyThread();
        myThread1.start();

        Test.MyThread myThread2 = new Test.MyThread();
        myThread2.start();
    }
}
