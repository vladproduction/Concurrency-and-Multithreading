package com.vladproduction.ch15_wait_notifyAll.advanced_example;

/**
 * Utility class for configuring broker system parameters.
 * Allows easy switching between different scenarios for producer-consumer interactions.
 */
public class BrokerConfig {

    // Private constructor to prevent instantiation
    private BrokerConfig() {}

    /**
     * Predefined scenarios for producer-consumer interaction
     */
    public enum Scenario {
        // Producer is faster than consumer
        HEAVY_PRODUCER(1, 3),

        // Consumer is faster than producer
        QUICK_CONSUMER(3, 1),

        // Balanced production and consumption
        BALANCED(3, 3);

        private final int producerSleepSeconds;
        private final int consumerSleepSeconds;

        Scenario(int producerSleepSeconds, int consumerSleepSeconds) {
            this.producerSleepSeconds = producerSleepSeconds;
            this.consumerSleepSeconds = consumerSleepSeconds;
        }

        public int getProducerSleepSeconds() {
            return producerSleepSeconds;
        }

        public int getConsumerSleepSeconds() {
            return consumerSleepSeconds;
        }
    }

    // Default configuration
    private static Scenario currentScenario = Scenario.BALANCED;
    private static int maxStoredMessages = 15;

    /**
     * Set the active scenario for the broker system
     * @param scenario The scenario to use
     */
    public static void setScenario(Scenario scenario) {
        currentScenario = scenario;
    }

    /**
     * @return Current producer sleep time in seconds
     */
    public static int getProducerSleepSeconds() {
        return currentScenario.getProducerSleepSeconds();
    }

    /**
     * @return Current consumer sleep time in seconds
     */
    public static int getConsumerSleepSeconds() {
        return currentScenario.getConsumerSleepSeconds();
    }

    /**
     * Set the maximum capacity of messages in the broker
     * @param capacity Maximum number of messages
     */
    public static void setMaxStoredMessages(int capacity) {
        maxStoredMessages = capacity;
    }

    /**
     * @return Maximum number of messages that can be stored in broker
     */
    public static int getMaxStoredMessages() {
        return maxStoredMessages;
    }

}
