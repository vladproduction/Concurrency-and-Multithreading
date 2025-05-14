package com.vladproduction.concurrency_design_patterns.producer_consumer_pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            while (true) {
                int value = queue.take();
                if(value == -1) break; //to check if the queue has no elements, the loop is stopped
                System.out.println("Consuming: " + value);
                TimeUnit.MILLISECONDS.sleep(1000L);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
