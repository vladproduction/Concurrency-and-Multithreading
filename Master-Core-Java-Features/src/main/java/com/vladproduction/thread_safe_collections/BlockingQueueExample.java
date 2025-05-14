package com.vladproduction.thread_safe_collections;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class BlockingQueueExample {
    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(3);

        Thread produserThread = new Thread(()->producer(queue));
        Thread consumerThread = new Thread(()->consumer(queue));

        produserThread.start();
        consumerThread.start();

        try {
            produserThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private static void producer(BlockingQueue<Integer> queue){
        for (int i = 1; i <= 5; i++) {
            try {
                queue.put(i); //if the queue is full, this will block until space is available
                System.out.println("Produced: " + i);
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    private static void consumer(BlockingQueue<Integer> queue){
        for (int i = 1; i <= 5; i++) {
            try {
                Integer take = queue.take();
                System.out.println("Consumed: " + take);
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
