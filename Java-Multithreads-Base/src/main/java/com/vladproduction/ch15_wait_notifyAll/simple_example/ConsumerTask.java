package com.vladproduction.ch15_wait_notifyAll.simple_example;

public class ConsumerTask implements Runnable {

    private final SharedResource resource;

    public ConsumerTask(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            resource.consume();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
