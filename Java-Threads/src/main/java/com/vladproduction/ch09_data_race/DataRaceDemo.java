package com.vladproduction.ch09_data_race;

/**
 * two shoppers adding items to a shared notepad;
 * garlicCount is not atomic, so it`s critical section and result will incorrect;
 * */
public class DataRaceDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread barron = new Shopper();
        Thread olivia = new Shopper();

        barron.start();
        olivia.start();

        barron.join();
        olivia.join();

        System.out.println("We should buy " + Shopper.garlicCount + " garlic.");

        //used Atomic:
        Thread barronThread = new ShopperAtomic();
        Thread oliviaThread = new ShopperAtomic();

        barronThread.start();
        oliviaThread.start();

        barronThread.join();
        oliviaThread.join();
        System.out.println("We should buy (atomic) " + ShopperAtomic.atomicIntGarlicCounter + " garlic.");


    }
}
