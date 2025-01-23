package com.vladproduction.wait_notify.produce_consume_advance.consumer;

import com.vladproduction.wait_notify.produce_consume_advance.broker.MessageBroker;
import com.vladproduction.wait_notify.produce_consume_advance.model.Message;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable{
    public static final int DURATION_BEFORE_CONSUMING = 1;


    private MessageBroker messageBroker;
    private int minimalAmountMessagesToConsume;

    private String name;

    public MessageConsumingTask(MessageBroker messageBroker,
                                int minimalAmountMessagesToConsume, String name) {
        this.messageBroker = messageBroker;
        this.minimalAmountMessagesToConsume = minimalAmountMessagesToConsume;
        this.name = name;
    }

    public int getMinimalAmountMessagesToConsume() {
        return minimalAmountMessagesToConsume;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try{
            while (!Thread.currentThread().isInterrupted()){
                TimeUnit.SECONDS.sleep(DURATION_BEFORE_CONSUMING);
                Optional<Message> optionalConsumedMessage = messageBroker.consume(this);
                optionalConsumedMessage.orElseThrow(MessageConsumingException::new);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }

    }


}
