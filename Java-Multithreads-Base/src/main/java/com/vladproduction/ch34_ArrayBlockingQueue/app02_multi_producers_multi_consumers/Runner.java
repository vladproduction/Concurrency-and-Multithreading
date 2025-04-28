package com.vladproduction.ch34_ArrayBlockingQueue.app02_multi_producers_multi_consumers;

public class Runner {
    public static void main(String[] args) {

        int initialQueueSize = 10;
        SharedQueueStorage sharedQueueStorage = new SharedQueueStorage(initialQueueSize);

        int numProducers = 3;
        int numConsumers = 2;

        // Create and start producer threads
        createProducersThreads(numProducers, sharedQueueStorage);


        // Create and start consumer threads
        createConsumersThreads(numConsumers, sharedQueueStorage);

    }

    private static void createConsumersThreads(int numConsumers, SharedQueueStorage sharedQueueStorage) {
        for (int i = 1; i <= numConsumers; i++) {
            ConsumerWorker consumer = new ConsumerWorker(sharedQueueStorage, i);
            Thread consumerThread = new Thread(consumer, "Consumer-" + i);
            consumerThread.start();
        }
    }

    private static void createProducersThreads(int numProducers, SharedQueueStorage sharedQueueStorage) {
        for (int i = 1; i <= numProducers; i++) {
            ProducerWorker producer = new ProducerWorker(sharedQueueStorage, i);
            Thread producerThread = new Thread(producer, "Producer-" + i);
            producerThread.start();
        }
    }
}
