package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

import java.util.concurrent.TimeUnit;

/**
 * Task responsible for consuming messages from the broker.
 * The consumption speed can be configured to simulate different scenarios.
 */
public class ConsumingTask implements Runnable{

    private final Broker broker;
    private final int delayBeforeConsuming;
    private final String messageConsumedFormat;

    /**
     * Creates a new ConsumingTask with specified parameters.
     *
     * @param broker the broker to consume messages from
     * @param delayBeforeConsuming delay in seconds between message consumption
     * @param messageConsumedFormat format string for logging consumed messages
     */
    public ConsumingTask(Broker broker, int delayBeforeConsuming, String messageConsumedFormat) {
        this.broker = broker;
        this.delayBeforeConsuming = delayBeforeConsuming;
        this.messageConsumedFormat = messageConsumedFormat;
    }

    @Override
    public void run() {
        try{
            while (!Thread.currentThread().isInterrupted()){
                TimeUnit.SECONDS.sleep(delayBeforeConsuming);
                Message consumedMessage = broker.consume();
                System.out.printf(messageConsumedFormat, consumedMessage);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
}
