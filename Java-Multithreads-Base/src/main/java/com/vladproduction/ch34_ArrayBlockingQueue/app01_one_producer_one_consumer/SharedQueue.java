package com.vladproduction.ch34_ArrayBlockingQueue.app01_one_producer_one_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SharedQueue {

    private final BlockingQueue<Integer> queue;

    public SharedQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>( capacity);
    }

    public int getSize(){
        return this.queue.size();
    }

    public void produce(int value) throws InterruptedException {
        this.queue.put(value);
    }

    public int consume() throws InterruptedException {
        return this.queue.take();
    }
}
