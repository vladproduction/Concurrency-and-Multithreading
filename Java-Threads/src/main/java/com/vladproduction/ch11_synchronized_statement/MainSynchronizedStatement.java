package com.vladproduction.ch11_synchronized_statement;

public class MainSynchronizedStatement {
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new ShopperThread();
        Thread thread2 = new ShopperThread();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("We should buy: " + ShopperThread.garlicCounter + " garlic.");

    }
}
