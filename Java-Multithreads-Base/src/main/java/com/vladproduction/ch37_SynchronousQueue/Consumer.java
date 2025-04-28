package com.vladproduction.ch37_SynchronousQueue;

/**
 * Consumes messages from the queue
 * */
public class Consumer implements Runnable {

    private final java.util.concurrent.SynchronousQueue<String> queue;
    private volatile boolean running = true;

    public Consumer(java.util.concurrent.SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            // Add a small delay to demonstrate the synchronous nature
            Thread.sleep(500);

            while (running) {
                System.out.println(Thread.currentThread().getName() +
                        " is waiting to take an element...");

                // Try to take a message from the queue
                // This will block until a producer puts one
                String message = queue.take();

                System.out.println(Thread.currentThread().getName() +
                        " has taken: " + message);

                // Process the message (simulated by sleeping)
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }

        System.out.println(Thread.currentThread().getName() + " is stopping.");
    }

}
