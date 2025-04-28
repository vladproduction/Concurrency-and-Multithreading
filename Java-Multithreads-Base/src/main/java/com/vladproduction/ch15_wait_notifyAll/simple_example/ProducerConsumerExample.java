package com.vladproduction.ch15_wait_notifyAll.simple_example;

public class ProducerConsumerExample {
    public static void main(String[] args) {

        SharedResource sharedResource = new SharedResource();

        Thread producerThread = new Thread(new ProducerTask(sharedResource));
        Thread consumerThread = new Thread(new ConsumerTask(sharedResource));

        producerThread.start();
        consumerThread.start();

    }
}
