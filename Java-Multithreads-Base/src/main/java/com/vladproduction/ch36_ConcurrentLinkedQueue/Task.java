package com.vladproduction.ch36_ConcurrentLinkedQueue;

/**
 * Task class representing work items in the queue
 */
public class Task {

    private final int id;
    private final int producerId;

    public Task(int id, int producerId) {
        this.id = id;
        this.producerId = producerId;
    }

    public int getId() {
        return id;
    }

    public int getProducerId() {
        return producerId;
    }

    @Override
    public String toString() {
        return "Task-" + id + " (from Producer-" + producerId + ")";
    }

    // Simulate work being done on this task
    public void process() throws InterruptedException {
        // Random processing time between 100-300ms
        Thread.sleep(100 + (int)(Math.random() * 200));
    }

}
