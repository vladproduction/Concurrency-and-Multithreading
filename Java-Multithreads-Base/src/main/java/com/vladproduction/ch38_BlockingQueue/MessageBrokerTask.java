package com.vladproduction.ch38_BlockingQueue;

import java.util.concurrent.TimeUnit;

public abstract class MessageBrokerTask<T> implements Runnable {

    private MessageBroker<T> broker;
    private long secondTimout;

    public MessageBrokerTask(MessageBroker<T> broker, long secondTimout) {
        this.broker = broker;
        this.secondTimout = secondTimout;
    }

    protected abstract void executeOperation(MessageBroker<T> broker) throws InterruptedException;

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try{
            while (true){
                executeOperation(broker);
                TimeUnit.SECONDS.sleep(secondTimout);
            }
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }


    }
}
