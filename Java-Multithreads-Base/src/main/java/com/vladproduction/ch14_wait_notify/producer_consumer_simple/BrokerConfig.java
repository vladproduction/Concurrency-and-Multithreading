package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

/**
 * Utility class that centralizes all configuration parameters for the broker system.
 * This allows for easy adjustment of producer and consumer speeds, as well as broker capacity.
 */
public final class BrokerConfig {

    // Private constructor to prevent instantiation
    private BrokerConfig() {}

    /**
     * Predefined scenarios for different production-consumption patterns
     */
    public enum Scenario {
        /** Producer creates messages much faster than the consumer can process them */
        HEAVY_PRODUCING(1, 5, 5),

        /** Consumer processes messages faster than the producer creates them */
        QUICK_CONSUMER(3, 1, 5),

        /** Both producer and consumer operate at similar speeds */
        BALANCED(2, 2, 5);

        private final int producerDelay;
        private final int consumerDelay;
        private final int brokerCapacity;

        Scenario(int producerDelay, int consumerDelay, int brokerCapacity) {
            this.producerDelay = producerDelay;
            this.consumerDelay = consumerDelay;
            this.brokerCapacity = brokerCapacity;
        }

        public int getProducerDelay() {
            return producerDelay;
        }

        public int getConsumerDelay() {
            return consumerDelay;
        }

        public int getBrokerCapacity() {
            return brokerCapacity;
        }

    }

    // Default formatting strings
    public static final String MESSAGE_PRODUCED_FORMAT = "Message '%s' produced\n";
    public static final String MESSAGE_CONSUMED_FORMAT = "Message '%s' consumed\n";

    // Default message template
    public static final String MESSAGE_TEMPLATE = "Message#%d";

}
