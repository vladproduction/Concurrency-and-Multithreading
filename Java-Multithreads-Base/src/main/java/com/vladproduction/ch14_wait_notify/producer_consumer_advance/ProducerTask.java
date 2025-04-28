package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

import java.util.concurrent.TimeUnit;

public class ProducerTask implements Runnable {

    private final Broker broker;
    private final MessageFactory messageFactory;
    private final int maximalAmountMessagesToProduce;
    private final String name;

    public static final int DURATION_TO_SLEEP_BEFORE_PRODUCING = 1;

    public ProducerTask(Broker broker, MessageFactory messageFactory,
                        int maximalAmountMessagesToProduce, String name) {
        this.broker = broker;
        this.messageFactory = messageFactory;
        this.maximalAmountMessagesToProduce = maximalAmountMessagesToProduce;
        this.name = name;
    }

    public int getMaximalAmountMessagesToProduce() {
        return maximalAmountMessagesToProduce;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message producedMessage = messageFactory.create();
                TimeUnit.SECONDS.sleep(DURATION_TO_SLEEP_BEFORE_PRODUCING);
                broker.produce(producedMessage, this);

            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

}
