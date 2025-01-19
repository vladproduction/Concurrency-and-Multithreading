package com.vladproduction._10_synchronized_method;

public class ShopperPerson extends Thread{

    static int garlicCount = 0;

    //we have to have static keyword for this method to correct operate with the data associated with the class
    //          but not only with the concrete instance; in our case thread
    private static synchronized void addGarlic(){
        garlicCount++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
             addGarlic();
        }
    }
}
