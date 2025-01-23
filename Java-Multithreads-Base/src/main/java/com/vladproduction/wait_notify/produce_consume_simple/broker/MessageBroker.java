package com.vladproduction.wait_notify.produce_consume_simple.broker;

import com.vladproduction.wait_notify.produce_consume_simple.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

public final class MessageBroker {
    private Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(Message message) {
        try{
            while (messagesToBeConsumed.size()>= maxStoredMessages){
                wait();
            }
            messagesToBeConsumed.add(message);
            notify();
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Message consume(){
        try{
            while (messagesToBeConsumed.isEmpty()){
                wait();
            }
            Message consumedMessage = this.messagesToBeConsumed.poll();
            notify();
            return consumedMessage;
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
            throw new RuntimeException(interruptedException);
        }
    }
}
