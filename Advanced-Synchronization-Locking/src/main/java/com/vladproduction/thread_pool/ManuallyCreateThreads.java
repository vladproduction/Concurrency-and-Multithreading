package com.vladproduction.thread_pool;

public class ManuallyCreateThreads {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new VegetableChopper().start();
        }

    }
}

class VegetableChopper extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Vegetable chopper");
    }
}
