package com.vladproduction.ch38_BlockingQueue;


import java.util.concurrent.SynchronousQueue;

public class SynchronousMessageBroker<T> extends MessageBroker<T>{

    public SynchronousMessageBroker() {
        super(new SynchronousQueue<>());
    }

}
