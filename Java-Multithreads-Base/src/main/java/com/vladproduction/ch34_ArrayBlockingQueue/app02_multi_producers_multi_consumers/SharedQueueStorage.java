package com.vladproduction.ch34_ArrayBlockingQueue.app02_multi_producers_multi_consumers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedQueueStorage {

    private final BlockingQueue<Item> queue;
    private final AtomicInteger totalProduced = new AtomicInteger(0);
    private final AtomicInteger totalConsumed = new AtomicInteger(0);

    public SharedQueueStorage(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    //produced method
    public void produce(Item item) throws InterruptedException {
        this.queue.put(item);
        totalProduced.incrementAndGet();
    }

    //consumed method
    public Item consume() throws InterruptedException {
        Item item = this.queue.take();
        totalConsumed.incrementAndGet();
        return item;
    }

    public int getTotalProduced() {
        return totalProduced.get();
    }

    public int getTotalConsumed() {
        return totalConsumed.get();
    }

    public int getQueueSize(){
        return this.queue.size();
    }
}
