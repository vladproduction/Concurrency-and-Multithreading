package com.vladproduction.ch38_BlockingQueue;

import java.util.function.Supplier;

public class MessageBrokerProducingTask<T> extends MessageBrokerTask<T> {

    private final Supplier<T> messageSupplier;

    public MessageBrokerProducingTask(MessageBroker<T> broker, long secondTimout, Supplier<T> messageSupplier) {
        super(broker, secondTimout);
        this.messageSupplier = messageSupplier;
    }

    @Override
    protected void executeOperation(MessageBroker<T> broker) throws InterruptedException {
        T message = messageSupplier.get();
        broker.put(message);
        System.out.printf("\nProduced message: %s", message);
    }
}
