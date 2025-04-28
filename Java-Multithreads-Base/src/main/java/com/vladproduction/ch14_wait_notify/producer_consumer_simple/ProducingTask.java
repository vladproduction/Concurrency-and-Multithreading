package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

import java.util.concurrent.TimeUnit;

/**
 * Task responsible for producing messages and sending them to the broker.
 * The production speed can be configured to simulate different scenarios.
 */
public class ProducingTask implements Runnable{

    private final Broker broker;
    private final MessageFactory messageFactory;
    private final int delayBeforeProducing;
    private final String messageProducedFormat;

    /**
     * Creates a new ProducingTask with specified parameters.
     *
     * @param broker the broker to send messages to
     * @param delayBeforeProducing delay in seconds between message production
     * @param messageProducedFormat format string for logging produced messages
     */
    public ProducingTask(Broker broker, int delayBeforeProducing, String messageProducedFormat) {
        this.broker = broker;
        this.messageFactory = new MessageFactory();
        this.delayBeforeProducing = delayBeforeProducing;
        this.messageProducedFormat = messageProducedFormat;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message producedMessage = messageFactory.create();
                TimeUnit.SECONDS.sleep(delayBeforeProducing);
                broker.produce(producedMessage);
                System.out.printf(messageProducedFormat, producedMessage);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Factory for creating uniquely numbered messages.
     */
    public static final class MessageFactory {

        private static final int INITIAL_MESSAGE_INDEX = 1;
        private int nextMessageIndex;

        public MessageFactory() {
            this.nextMessageIndex = INITIAL_MESSAGE_INDEX;
        }

        public Message create() {
            return new Message(String.format(BrokerConfig.MESSAGE_TEMPLATE, nextMessageIndex++));
        }
    }
}
