package com.vladproduction.ch15_wait_notifyAll.simple_example;

public class SharedResource {

    private int content;
    private boolean available = false;

    public synchronized void produce(int value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        content = value;
        available = true;
        System.out.println("Produced: " + content);
        notifyAll();
    }

    public synchronized void consume() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Consumed: " + content);
        available = false;
        notifyAll();
    }


}
