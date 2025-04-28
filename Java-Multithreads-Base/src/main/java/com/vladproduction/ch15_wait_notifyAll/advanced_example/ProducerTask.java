package com.vladproduction.ch15_wait_notifyAll.advanced_example;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Enhanced producer task with variable production rates
 * to better demonstrate wait-notifyAll patterns.
 */
public class ProducerTask implements Runnable {

    private final Broker broker;
    private final MessageFactory messageFactory;
    private final int maximalAmountMessagesToProduce;
    private final String name;
    private final Random random = new Random();

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
     * Creates messages and sends them to the broker at a variable rate
     * to demonstrate wait-notifyAll patterns.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Create a new message
                Message producedMessage = messageFactory.create();

                // Sleep for a variable amount of time before producing
                int sleepTime = 1 + random.nextInt(3); // 1-3 seconds
                TimeUnit.SECONDS.sleep(sleepTime);

                // Produce the message
                broker.produce(producedMessage, this);

                // Log producer action
                System.out.printf("[%s] Produced message after %d seconds, now sleeping...\n",
                        name, sleepTime);
            }
        } catch (InterruptedException interruptedException) {
            System.out.printf("[%s] Producer interrupted and shutting down\n", name);
            Thread.currentThread().interrupt();
        }
    }

}