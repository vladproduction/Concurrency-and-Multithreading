package com.vladproduction.wait_notify.produce_consume_simple.consumer;

import com.vladproduction.wait_notify.produce_consume_simple.broker.MessageBroker;
import com.vladproduction.wait_notify.produce_consume_simple.model.Message;

import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable{
    public static final int DURATION_BEFORE_CONSUMING = 1;
    public static final String MESSAGE_IS_CONSUMED = "Message '%s' is consumed\n";

    private MessageBroker messageBroker;

    public MessageConsumingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        try{
            while (!Thread.currentThread().isInterrupted()){
                TimeUnit.SECONDS.sleep(DURATION_BEFORE_CONSUMING);
                Message consumedMessage = messageBroker.consume();
                System.out.printf(MESSAGE_IS_CONSUMED, consumedMessage);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }

    }
}
