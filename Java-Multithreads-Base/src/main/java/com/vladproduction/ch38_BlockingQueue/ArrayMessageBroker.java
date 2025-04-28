package com.vladproduction.ch38_BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayMessageBroker<T> extends MessageBroker<T> {

    public ArrayMessageBroker(int capacity) {
        super(new ArrayBlockingQueue<>(capacity));
    }
}
