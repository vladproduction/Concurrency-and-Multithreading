package com.vladproduction.threads_demo;

public class VolatileExample {
    public static void main(String[] args) {
        VolatileExample volatileExample = new VolatileExample();
        Thread thread = new Thread(volatileExample::run);
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        volatileExample.stopRunning();
    }

    private volatile boolean running = true;

    public void stopRunning(){
        running = false;
    }

    public void run(){
        while (running){
            System.out.println("Thread is running...");
        }
        System.out.println("Thread is stopped...");
    }

}
