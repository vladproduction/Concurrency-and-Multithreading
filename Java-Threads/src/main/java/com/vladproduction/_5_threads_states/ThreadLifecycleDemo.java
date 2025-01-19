package com.vladproduction._5_threads_states;

/**
 * Two threads cooking soup;
 * */
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Barron started and requested for Olivia`s help.");
//        OliviaCooker oliviaCooker = new OliviaCooker();// in case extends Thread
        Thread oliviaCooker = new Thread(new OliviaCooker());// in case implements Runnable
        System.out.println(" Olivia status: " + oliviaCooker.getState());

        System.out.println("Barron tells Oliva to start.");
        oliviaCooker.start();
        System.out.println(" Olivia status: " + oliviaCooker.getState());

        System.out.println("Barron continues to cooking soup.");
        Thread.sleep(500);
        System.out.println(" Olivia status: " + oliviaCooker.getState());

        System.out.println("Barron patiently wait for Olivia to finish and join.");
        oliviaCooker.join();
        System.out.println(" Olivia status: " + oliviaCooker.getState());

        System.out.println("Barron and Olivia are both done!");
        System.out.println(" Olivia status: " + oliviaCooker.getState());
        System.out.println(" Barron status (main thread): " + Thread.currentThread().getState());

    }
}
