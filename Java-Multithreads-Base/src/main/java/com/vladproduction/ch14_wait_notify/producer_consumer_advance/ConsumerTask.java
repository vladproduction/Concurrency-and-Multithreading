package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ConsumerTask implements Runnable{

    private final Broker broker;
    private final int minimalAmountMessagesToConsume;
    private final String name;

    public static final int DURATION_TO_SLEEP_BEFORE_CONSUMING = 3;

    public ConsumerTask(Broker broker,
                        int minimalAmountMessagesToConsume, String name) {
        this.broker = broker;
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
                TimeUnit.SECONDS.sleep(DURATION_TO_SLEEP_BEFORE_CONSUMING);
                Optional<Message> optionalConsumedMessage = broker.consume(this);
                optionalConsumedMessage.orElseThrow(MessageConsumingException::new);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }

    }


}
