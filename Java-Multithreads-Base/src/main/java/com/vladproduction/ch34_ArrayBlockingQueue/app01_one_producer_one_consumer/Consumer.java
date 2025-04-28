package com.vladproduction.ch34_ArrayBlockingQueue.app01_one_producer_one_consumer;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private final SharedQueue sharedQueue;

    public Consumer(SharedQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        try{

            for (int i = 1; i <= 10; i++) {
                int consumedValue = sharedQueue.consume();
                System.out.printf("Consumer: Consumed %s\n", consumedValue);
                System.out.printf("Queue size after consuming: %d\n", sharedQueue.getSize());

                // Sleep for a random time between 0 and 1 second
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
            }


        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Consumer interrupted: " + e.getMessage());
        }
    }
}
