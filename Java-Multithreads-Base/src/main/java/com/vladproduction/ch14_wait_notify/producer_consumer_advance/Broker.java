package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public final class Broker {

    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;


    public static final String MESSAGE_IS_PRODUCED = "Message '%s' is produced by producer '%s'. " +
            "Amount messages before producing: '%d'\n";
    public static final String MESSAGE_IS_CONSUMED = "Message '%s' is consumed by consumer '%s'. " +
            "Amount messages before consuming: '%d'\n";


    public Broker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(Message message, ProducerTask producingTask) {
        try{
            while (!isShouldProduce(producingTask)){
                wait();
            }
            messagesToBeConsumed.add(message);
            System.out.printf(MESSAGE_IS_PRODUCED, message, producingTask.getName(), this.messagesToBeConsumed.size() - 1);
            notify();
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Optional<Message> consume(ConsumerTask consumingTask){
        try{
            while (!isShouldConsume(consumingTask)){
                wait();
            }
            Message consumedMessage = this.messagesToBeConsumed.poll();
            System.out.printf(MESSAGE_IS_CONSUMED, consumedMessage, consumingTask.getName(), this.messagesToBeConsumed.size() + 1);
            notify();
            return Optional.ofNullable(consumedMessage);
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private boolean isShouldConsume(ConsumerTask consumingTask){
        return !this.messagesToBeConsumed.isEmpty()
                && this.messagesToBeConsumed.size() >= consumingTask.getMinimalAmountMessagesToConsume();
    }

    private boolean isShouldProduce(ProducerTask producingTask){
        return this.messagesToBeConsumed.size() < this.maxStoredMessages
                && this.messagesToBeConsumed.size() <= producingTask.getMaximalAmountMessagesToProduce();
    }
}
