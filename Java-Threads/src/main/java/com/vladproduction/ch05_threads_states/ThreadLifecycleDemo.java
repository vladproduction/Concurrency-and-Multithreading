package com.vladproduction.ch05_threads_states;

/**
 * Two threads cooking soup;
 * */
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {

        //check main thread status:
        System.out.println("Barron (main thread) status: " + Thread.currentThread().getState());

        System.out.println("Barron (as main thread) started and requested for Olivia`s help.");
//        OliviaCooker oliviaCooker = new OliviaCooker();// in case extends Thread
        Thread oliviaCooker = new Thread(new OliviaCooker());// in case implements Runnable --> we are now!
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
