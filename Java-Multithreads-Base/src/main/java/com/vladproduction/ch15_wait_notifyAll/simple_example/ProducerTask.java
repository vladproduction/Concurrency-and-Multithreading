package com.vladproduction.ch15_wait_notifyAll.simple_example;

public class ProducerTask implements Runnable {

    private final SharedResource resource;

    public ProducerTask(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
             resource.produce(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
