package com.vladproduction.ch33_blocking_queue_wait_notify;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SimpleBlockingQueue - A basic thread-safe queue implementation
 * Elements can be added with put() and removed with take()
 * take() will block if the queue is empty until an element becomes available
 */
public class SimpleBlockingQueue<T> {

    //storage for queued elements
    private final List<T> queue = new LinkedList<>();
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    /**
     * Retrieves and removes the head of the queue, waiting if necessary
     * until an element becomes available
     */
    public synchronized T take() throws InterruptedException{
        // Wait until the queue has elements or shutdown is signaled
        while (queue.isEmpty()) {
            if (isShutdown.get()) {
                return null; // Return null to signal shutdown
            }
            wait(); // Release lock and wait for notification
        }
        // Remove and return first element
        return queue.remove(0);
    }

    /**
     * Adds the specified element to the queue
     */
    public synchronized void put(T element) {
        if (isShutdown.get()) {
            return; // Don't accept new elements if shutdown
        }
        // Add element to end of queue
        queue.add(element);

        // Notify one waiting thread that an element is available
        notify();
    }

    /**
     * Signals that no more elements will be added and workers should exit
     * when the queue is empty
     */
    public synchronized void shutdown() {
        isShutdown.set(true);
        notifyAll(); // Wake up all waiting threads
    }

    /**
     * Returns the number of elements in the queue
     */
    public synchronized int size() {
        return queue.size();
    }

}
