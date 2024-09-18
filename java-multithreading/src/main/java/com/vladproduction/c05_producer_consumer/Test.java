package com.vladproduction.c05_producer_consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * base on ArrayBlockingQueue class
 * */
public class Test {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        startTreads();


    }

    private static void startTreads() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }



    private static void produce() throws InterruptedException {
        while (true){
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        while (true){
            Thread.sleep(100);
            if (random.nextInt(10) == 5){
                System.out.println(queue.take());
                System.out.println("queue size: " + queue.size());
            }
        }

    }

}
