package com.vladproduction.ch09_data_race;

public class Shopper extends Thread{

    static int garlicCount = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100_000; i++) {
             garlicCount++;
        }
    }

    //solution:
    /*@Override
    public void run() {
        synchronized (Shopper.class) {
            for (int i = 0; i < 100_000; i++) {
                garlicCount++;
            }
        }
    }*/
}
