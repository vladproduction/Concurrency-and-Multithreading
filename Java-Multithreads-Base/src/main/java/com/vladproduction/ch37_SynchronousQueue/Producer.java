package com.vladproduction.ch37_SynchronousQueue;

/**
 * Produces messages and puts them in the queue
 * */
public class Producer implements Runnable {

    private final java.util.concurrent.SynchronousQueue<String> queue;
    private volatile boolean running = true;

    public Producer(java.util.concurrent.SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        int messageCount = 1;

        try {
            while (running) {
                // Create a message
                String message = "Message #" + messageCount;

                System.out.println(Thread.currentThread().getName() +
                        " is attempting to put: " + message);

                // Try to put the message in the queue
                // This will block until a consumer takes it
                queue.put(message);

                System.out.println(Thread.currentThread().getName() +
                        " has put: " + message);

                messageCount++;

                // Sleep for a bit before producing next message
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }

        System.out.println(Thread.currentThread().getName() + " is stopping.");
    }

}
