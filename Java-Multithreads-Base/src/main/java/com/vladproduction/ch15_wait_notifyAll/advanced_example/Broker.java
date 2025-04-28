package com.vladproduction.ch15_wait_notifyAll.advanced_example;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Enhanced broker implementation using wait-notifyAll pattern
 * for more robust handling of multiple producers and consumers.
 */
public class Broker {

    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;

    // Log message templates
    private static final String MESSAGE_PRODUCED_TEMPLATE =
            "Message '%s' is produced by producer '%s'. Amount messages before producing: '%d'\n";
    private static final String MESSAGE_CONSUMED_TEMPLATE =
            "Message '%s' is consumed by consumer '%s'. Amount messages before consuming: '%d'\n";
    private static final String QUEUE_STATUS_TEMPLATE =
            "[STATUS] Queue size: %d/%d | Producers waiting: %s | Consumers waiting: %s\n";

    // Flags to track waiting threads
    private int waitingProducersCount = 0;
    private int waitingConsumersCount = 0;

    /**
     * Creates a new broker with specified capacity
     * @param maxStoredMessages Maximum number of messages that can be stored in the broker
     */
    public Broker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    /**
     * Produces a message and adds it to the queue
     * Will wait if conditions for production are not met
     * Uses notifyAll to wake up all waiting threads when state changes
     *
     * @param message Message to be produced
     * @param producerTask Producer that is producing the message
     */
    public synchronized void produce(Message message, ProducerTask producerTask) {
        try {
            // If can't produce, wait until conditions change
            while (!canProduce(producerTask)) {
                waitingProducersCount++;
                printQueueStatus();
                wait();
                waitingProducersCount--;
            }

            // Add message to queue
            messagesToBeConsumed.add(message);
            System.out.printf(MESSAGE_PRODUCED_TEMPLATE,
                    message, producerTask.getName(), this.messagesToBeConsumed.size() - 1);

            // Notify all waiting threads that queue state has changed
            printQueueStatus();
            notifyAll();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Consumes a message from the queue
     * Will wait if conditions for consumption are not met
     * Uses notifyAll to wake up all waiting threads when state changes
     *
     * @param consumerTask Consumer that is consuming the message
     * @return Optional containing the consumed message, or empty if none available
     */
    public synchronized Optional<Message> consume(ConsumerTask consumerTask) {
        try {
            // If can't consume, wait until conditions change
            while (!canConsume(consumerTask)) {
                waitingConsumersCount++;
                printQueueStatus();
                wait();
                waitingConsumersCount--;
            }

            // Get message from queue
            Message consumedMessage = this.messagesToBeConsumed.poll();
            System.out.printf(MESSAGE_CONSUMED_TEMPLATE,
                    consumedMessage, consumerTask.getName(), this.messagesToBeConsumed.size() + 1);

            // Notify all waiting threads that queue state has changed
            printQueueStatus();
            notifyAll();

            return Optional.ofNullable(consumedMessage);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    /**
     * Checks if consumption conditions are met
     * @param consumerTask Consumer trying to consume
     * @return true if the consumer can consume message, false otherwise
     */
    private boolean canConsume(ConsumerTask consumerTask) {
        return !this.messagesToBeConsumed.isEmpty()
                && this.messagesToBeConsumed.size() >= consumerTask.getMinimalAmountMessagesToConsume();
    }

    /**
     * Checks if production conditions are met
     * @param producerTask Producer trying to produce
     * @return true if the producer can produce message, false otherwise
     */
    private boolean canProduce(ProducerTask producerTask) {
        return this.messagesToBeConsumed.size() < this.maxStoredMessages
                && this.messagesToBeConsumed.size() <= producerTask.getMaximalAmountMessagesToProduce();
    }

    /**
     * Prints the current status of the message queue
     * Shows queue size, capacity, and number of waiting threads
     */
    private void printQueueStatus() {
        System.out.printf(QUEUE_STATUS_TEMPLATE,
                messagesToBeConsumed.size(),
                maxStoredMessages,
                waitingProducersCount > 0 ? waitingProducersCount + " waiting" : "none",
                waitingConsumersCount > 0 ? waitingConsumersCount + " waiting" : "none");
    }

}
