package com.vladproduction.ch37_SynchronousQueue;

/**
 * A SynchronousQueue is a special kind of blocking queue that doesn't actually store elements -
 * each put operation must wait for a corresponding take operation by another thread, and vice versa.
 * */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        // Create a shared SynchronousQueue
        java.util.concurrent.SynchronousQueue<String> queue =
                new java.util.concurrent.SynchronousQueue<>();

        // Create and start the producer thread
        Producer producer = new Producer(queue);
        Thread producerThread = new Thread(producer, "Producer");

        // Create and start the consumer thread
        Consumer consumer = new Consumer(queue);
        Thread consumerThread = new Thread(consumer, "Consumer");

        // Start both threads
        producerThread.start();
        consumerThread.start();

        // Let threads run for a while
        try {
            Thread.sleep(10000); // Run for 10 seconds

            // Signal threads to stop
            producer.stop();
            consumer.stop();

            // Wait for threads to finish
            producerThread.join();
            consumerThread.join();

            System.out.println("Demo completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
