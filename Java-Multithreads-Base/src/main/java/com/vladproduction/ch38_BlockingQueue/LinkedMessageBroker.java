package com.vladproduction.ch38_BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedMessageBroker<T> extends MessageBroker<T> {

    public LinkedMessageBroker(final int capacity) {
        super(new LinkedBlockingQueue<>(capacity));
    }

}
