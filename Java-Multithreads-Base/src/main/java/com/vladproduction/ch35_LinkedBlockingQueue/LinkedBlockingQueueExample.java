package com.vladproduction.ch35_LinkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This example demonstrates the usage of LinkedBlockingQueue in a producer-consumer scenario.
 * The producer adds items to the queue, and the consumer removes them.
 * The queue automatically handles the synchronization between threads.
 */
public class LinkedBlockingQueueExample {
    public static void main(String[] args) {

        int queueCapacity = 5;

        // Create a LinkedBlockingQueue with a capacity of 5
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(queueCapacity);

        // Create atomic flag to signal when to stop threads
        AtomicBoolean stopFlag = new AtomicBoolean(false);

        // Create and start producer thread
        Thread producerThread = new Thread(() -> {
            try {
                Producer.produce(queue, stopFlag);
            } catch (InterruptedException e) {
                Logger.log("Producer thread interrupted");
            }
        });

        // Create and start consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                Consumer.consume(queue, stopFlag);
            } catch (InterruptedException e) {
                Logger.log("Consumer thread interrupted");
            }
        });

        Logger.log("Starting producer and consumer threads");
        producerThread.start();
        consumerThread.start();

        // Let the threads run for 5 seconds
        try {
            Thread.sleep(5000);

            // Signal threads to stop
            Logger.log("Signaling threads to stop");
            stopFlag.set(true);

            // Wait for threads to finish
            producerThread.join();
            consumerThread.join();

            Logger.log("Example completed");
        } catch (InterruptedException e) {
            Logger.log("Main thread interrupted");
        }

    }
}
