package com.vladproduction.producer_consumer;

import java.util.concurrent.BlockingQueue;

public class SoupProducer extends Thread {

    private BlockingQueue servingLine;

    public SoupProducer(BlockingQueue servingLine) {
        this.servingLine = servingLine;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) { //20 bowls of soup
            try {
                servingLine.add("Bowl #" + i);
                System.out.printf("Served Bowl: %d - remaining capacity: %d\n", i, servingLine.remainingCapacity());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        servingLine.add("No more soup!");
        servingLine.add("No more soup!");
    }
}
