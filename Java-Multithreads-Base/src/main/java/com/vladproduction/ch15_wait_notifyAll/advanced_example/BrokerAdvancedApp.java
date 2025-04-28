package com.vladproduction.ch15_wait_notifyAll.advanced_example;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Main application class for demonstrating wait-notifyAll pattern
 * with multiple producers and consumers operating under different conditions.
 */
public class BrokerAdvancedApp {

    public static void main(String[] args) {
        // Configure the system for demonstration
        BrokerConfig.setMaxStoredMessages(5); // Small capacity to force waiting

        // Create our broker
        Broker broker = new Broker(BrokerConfig.getMaxStoredMessages());
        MessageFactory messageFactory = new MessageFactory();

        // Create multiple producer threads with different capacities
        Thread firstProducerThread = new Thread(
                new ProducerTask(broker, messageFactory, 3, "FAST_PRODUCER"));
        Thread secondProducerThread = new Thread(
                new ProducerTask(broker, messageFactory, 2, "SLOW_PRODUCER"));

        // Create multiple consumer threads with different thresholds
        Thread firstConsumerThread = new Thread(
                new ConsumerTask(broker, 0, "EAGER_CONSUMER"));
        Thread secondConsumerThread = new Thread(
                new ConsumerTask(broker, 4, "BULK_CONSUMER"));

        // Start all threads
        startThreads(firstProducerThread, secondProducerThread,
                firstConsumerThread, secondConsumerThread);

        // Run for a limited time then shutdown
        try {
            // Let the system run for 30 seconds
            TimeUnit.SECONDS.sleep(30);

            // Shutdown the system
            System.out.println("\n*** Shutting down the system ***\n");
            firstProducerThread.interrupt();
            secondProducerThread.interrupt();
            firstConsumerThread.interrupt();
            secondConsumerThread.interrupt();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Helper method to start multiple threads
     * @param threads Threads to be started
     */
    private static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

}
