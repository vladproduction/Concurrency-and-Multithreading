package com.vladproduction.concurrency_design_patterns.thread_per_message_pattern;

public class PrintRequestHandler implements Runnable{

    private final String message;

    public PrintRequestHandler(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " processing: " + message);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " completed: " + message);
    }
}
