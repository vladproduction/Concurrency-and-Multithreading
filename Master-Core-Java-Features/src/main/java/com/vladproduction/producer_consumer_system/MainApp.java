package com.vladproduction.producer_consumer_system;

public class MainApp {
    public static void main(String[] args) {

        SharedBuffer sharedBuffer = new SharedBuffer(5);

        Thread producerThread = new Thread(new Producer(sharedBuffer));
        Thread consumerThread = new Thread(new Consumer(sharedBuffer));
        producerThread.start();
        consumerThread.start();

    }
}
