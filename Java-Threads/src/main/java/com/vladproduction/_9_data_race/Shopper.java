package com.vladproduction._9_data_race;

public class Shopper extends Thread{

    static int garlicCount = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100_000; i++) {
             garlicCount++;
        }
    }
}
