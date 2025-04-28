package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Main broker class that manages the exchange of messages between producers and consumers.
 * Implements thread-safe message handling using wait/notify mechanism.
 */
public final class Broker {

    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;

    // Log message templates
    private static final String MESSAGE_PRODUCED_TEMPLATE =
            "Message '%s' is produced by producer '%s'. Amount messages before producing: '%d'\n";
    private static final String MESSAGE_CONSUMED_TEMPLATE =
            "Message '%s' is consumed by consumer '%s'. Amount messages before consuming: '%d'\n";

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
     *
     * @param message Message to be produced
     * @param producerTask Producer that is producing the message
     */
    public synchronized void produce(Message message, ProducerTask producerTask) {
        try {
            while (!canProduce(producerTask)) {
                wait();
            }
            messagesToBeConsumed.add(message);
            System.out.printf(MESSAGE_PRODUCED_TEMPLATE,
                    message, producerTask.getName(), this.messagesToBeConsumed.size() - 1);
            notify();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Consumes a message from the queue
     * Will wait if conditions for consumption are not met
     *
     * @param consumerTask Consumer that is consuming the message
     * @return Optional containing the consumed message, or empty if none available
     */
    public synchronized Optional<Message> consume(ConsumerTask consumerTask) {
        try {
            while (!canConsume(consumerTask)) {
                wait();
            }
            Message consumedMessage = this.messagesToBeConsumed.poll();
            System.out.printf(MESSAGE_CONSUMED_TEMPLATE,
                    consumedMessage, consumerTask.getName(), this.messagesToBeConsumed.size() + 1);
            notify();
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

}
