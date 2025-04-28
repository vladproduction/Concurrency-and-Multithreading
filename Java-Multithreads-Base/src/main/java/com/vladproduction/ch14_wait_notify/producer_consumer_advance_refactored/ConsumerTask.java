package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Task responsible for consuming messages from the broker
 * at a configurable rate.
 */
public class ConsumerTask implements Runnable{

    private final Broker broker;
    private final int minimalAmountMessagesToConsume;
    private final String name;

    /**
     * Creates a new consumer task
     *
     * @param broker Broker to consume messages from
     * @param minimalAmountMessagesToConsume Minimum number of messages in the broker for this consumer to start consuming
     * @param name Name of this consumer for identification
     */
    public ConsumerTask(Broker broker, int minimalAmountMessagesToConsume, String name) {
        this.broker = broker;
        this.minimalAmountMessagesToConsume = minimalAmountMessagesToConsume;
        this.name = name;
    }

    /**
     * @return Minimum amount of messages that need to be in broker for this consumer to consume
     */
    public int getMinimalAmountMessagesToConsume() {
        return minimalAmountMessagesToConsume;
    }

    /**
     * @return Name of this consumer
     */
    public String getName() {
        return name;
    }

    /**
     * Main execution loop for the consumer.
     * Consumes messages from the broker at the configured rate.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(BrokerConfig.getConsumerSleepSeconds());
                Optional<Message> optionalConsumedMessage = broker.consume(this);
                optionalConsumedMessage.orElseThrow(MessageConsumingException::new);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

}
