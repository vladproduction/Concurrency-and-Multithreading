package com.vladproduction.ch15_wait_notifyAll.advanced_example;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Enhanced consumer task with variable consumption rates
 * to better demonstrate wait-notifyAll patterns.
 */
public class ConsumerTask implements Runnable {

    private final Broker broker;
    private final int minimalAmountMessagesToConsume;
    private final String name;
    private final Random random = new Random();

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
     * Consumes messages from the broker at a variable rate
     * to demonstrate wait-notifyAll patterns.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Sleep for a variable amount of time before consuming
                int sleepTime = 1 + random.nextInt(4); // 1-4 seconds
                TimeUnit.SECONDS.sleep(sleepTime);

                // Try to consume a message
                Optional<Message> optionalConsumedMessage = broker.consume(this);

                if (optionalConsumedMessage.isPresent()) {
                    // Log consumer action on success
                    System.out.printf("[%s] Consumed message after %d seconds, now sleeping...\n",
                            name, sleepTime);

                    // Process the message (simulated by doing nothing)
                } else {
                    // This branch should never be reached with our implementation,
                    // as we wait until a message is available
                    System.out.printf("[%s] Failed to consume message (unexpected)\n", name);
                }
            }
        } catch (InterruptedException interruptedException) {
            System.out.printf("[%s] Consumer interrupted and shutting down\n", name);
            Thread.currentThread().interrupt();
        } catch (MessageConsumingException consumeException) {
            System.out.printf("[%s] Error consuming message: %s\n",
                    name, consumeException.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
