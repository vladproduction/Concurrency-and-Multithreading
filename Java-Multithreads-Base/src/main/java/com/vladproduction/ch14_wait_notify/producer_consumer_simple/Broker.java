package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Broker class responsible for managing the message queue between producers and consumers.
 * It limits the maximum number of stored messages and implements thread-safe
 * message production and consumption using a wait/notify mechanism.
 */
public class Broker {

    private final Queue<Message> messageQueue;
    private final int maxCapacity;

    /**
     * Creates a new Broker with the specified maximum capacity for stored messages.
     *
     * @param maxCapacity the maximum number of messages that can be stored in the queue
     */
    public Broker(int maxCapacity) {
        this.messageQueue = new ArrayDeque<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds a message to the queue. If the queue is full, the thread will wait
     * until space becomes available.
     *
     * @param message the message to be added to the queue
     */
    public synchronized void produce(Message message){
        try{
            while (messageQueue.size() >= maxCapacity){
                wait(); // Wait until there's space in the queue
            }
            messageQueue.add(message);
            notify(); // Notify consumer that a message is available
        } catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Retrieves and removes a message from the queue. If the queue is empty,
     * the thread will wait until a message becomes available.
     *
     * @return the consumed message, or RuntimeException("Interrupted while waiting for message");
     */
    public synchronized Message consume(){
        try{
            while (messageQueue.isEmpty()){
                wait(); // Wait until a message is available
            }
            Message messageConsumed = messageQueue.poll();
            notify(); // Notify the producer that space is available
            return messageConsumed;
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for message");
        }
    }

}

