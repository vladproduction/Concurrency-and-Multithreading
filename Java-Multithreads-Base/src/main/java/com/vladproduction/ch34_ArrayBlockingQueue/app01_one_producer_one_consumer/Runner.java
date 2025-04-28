package com.vladproduction.ch34_ArrayBlockingQueue.app01_one_producer_one_consumer;

public class Runner {
    public static void main(String[] args) {

        int initialQueueSize = 5;
        SharedQueue sharedQueue = new SharedQueue(initialQueueSize);

        Producer producer = new Producer(sharedQueue);
        Consumer consumer = new Consumer(sharedQueue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();



    }
}
