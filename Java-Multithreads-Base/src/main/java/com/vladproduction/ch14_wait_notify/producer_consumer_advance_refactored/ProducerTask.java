package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

import java.util.concurrent.TimeUnit;

/**
 * Task responsible for producing messages at a configurable rate
 * and sending them to the broker.
 */
public class ProducerTask implements Runnable{

    private final Broker broker;
    private final MessageFactory messageFactory;
    private final int maximalAmountMessagesToProduce;
    private final String name;

    /**
     * Creates a new producer task
     *
     * @param broker Broker to send messages to
     * @param messageFactory Factory to create new messages
     * @param maximalAmountMessagesToProduce Maximum number of messages this producer can produce
     * @param name Name of this producer for identification
     */
    public ProducerTask(Broker broker, MessageFactory messageFactory,
                        int maximalAmountMessagesToProduce, String name) {
        this.broker = broker;
        this.messageFactory = messageFactory;
        this.maximalAmountMessagesToProduce = maximalAmountMessagesToProduce;
        this.name = name;
    }

    /**
     * @return Maximum number of messages this producer can produce
     */
    public int getMaximalAmountMessagesToProduce() {
        return maximalAmountMessagesToProduce;
    }

    /**
     * @return Name of this producer
     */
    public String getName() {
        return name;
    }

    /**
     * Main execution loop for the producer.
     * Creates messages and sends them to the broker at the configured rate.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message producedMessage = messageFactory.create();
                TimeUnit.SECONDS.sleep(BrokerConfig.getProducerSleepSeconds());
                broker.produce(producedMessage, this);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

}
