package com.vladproduction.ch34_ArrayBlockingQueue.app01_one_producer_one_consumer;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

    private final SharedQueue sharedQueue;

    public Producer(SharedQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        try{

            for (int i = 1; i <= 10; i++) {

                System.out.printf("Producer: Producing value %d\n", i);
                sharedQueue.produce(i);
                System.out.printf("Queue size after producing: %d\n", sharedQueue.getSize());

                // Sleep for a random time between 0 and 1 second
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
            }

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted: " + e.getMessage());
        }

    }
}
