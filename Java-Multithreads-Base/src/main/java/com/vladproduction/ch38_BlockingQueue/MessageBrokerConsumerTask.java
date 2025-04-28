package com.vladproduction.ch38_BlockingQueue;

public class MessageBrokerConsumerTask<T> extends MessageBrokerTask<T> {

    public MessageBrokerConsumerTask(MessageBroker<T> broker, long secondTimout) {
        super(broker, secondTimout);
    }

    @Override
    protected void executeOperation(MessageBroker<T> broker) throws InterruptedException {
        T message = broker.take();
        System.out.printf("\nConsumed message: %s", message);
    }

}
