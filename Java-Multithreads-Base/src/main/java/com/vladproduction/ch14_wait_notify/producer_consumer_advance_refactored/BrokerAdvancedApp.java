package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

import java.util.Arrays;

/**
 * Main application class for running the broker system with different
 * producer-consumer scenarios.
 */
public class BrokerAdvancedApp {

    public static void main(String[] args) {
        // Set the desired scenario here - EASY TO SWITCH BETWEEN SCENARIOS
//        BrokerConfig.setScenario(BrokerConfig.Scenario.HEAVY_PRODUCER);
//        BrokerConfig.setScenario(BrokerConfig.Scenario.QUICK_CONSUMER);
        BrokerConfig.setScenario(BrokerConfig.Scenario.BALANCED);

        // Configure broker capacity
        BrokerConfig.setMaxStoredMessages(15);

        // Create the broker
        Broker broker = new Broker(BrokerConfig.getMaxStoredMessages());
        MessageFactory messageFactory = new MessageFactory();

        // Create producer threads with different capacities
        Thread firstProducerThread = new Thread(
                new ProducerTask(broker, messageFactory, BrokerConfig.getMaxStoredMessages(), "PRODUCER_1"));
        Thread secondProducerThread = new Thread(
                new ProducerTask(broker, messageFactory, 10, "PRODUCER_2"));
        Thread thirdProducerThread = new Thread(
                new ProducerTask(broker, messageFactory, 5, "PRODUCER_3"));

        // Create consumer threads with different thresholds
        Thread firstConsumerThread = new Thread(
                new ConsumerTask(broker, 0, "CONSUMER_1"));
        Thread secondConsumerThread = new Thread(
                new ConsumerTask(broker, 6, "CONSUMER_2"));
        Thread thirdConsumerThread = new Thread(
                new ConsumerTask(broker, 11, "CONSUMER_3"));

        // Start all threads
        startThreads(firstProducerThread, secondProducerThread, thirdProducerThread,
                firstConsumerThread, secondConsumerThread, thirdConsumerThread);
    }

    /**
     * Helper method to start multiple threads
     * @param threads Threads to be started
     */
    private static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

}
