package com.vladproduction._5_threads_states;

/**
 * two ways to create threads:
 * 1) class OliviaCooker extends Thread Class;
 * 2) class OliviaCooker implements Runnable Interface;
 * */
public class OliviaCooker implements Runnable{


    @Override
    public void run() {
        System.out.println("Olivia is started & waiting for sausage to thaw...");
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Olivia is done cutting sausage.");
    }
}
