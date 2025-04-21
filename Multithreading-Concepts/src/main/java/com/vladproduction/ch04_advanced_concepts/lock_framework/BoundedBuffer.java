package com.vladproduction.ch04_advanced_concepts.lock_framework;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<E> {

    private final Queue<E> queue;
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    // Separate conditions for producers and consumers
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public void put(E item) throws InterruptedException {
        lock.lock();
        try {
            // Wait until there's space in the buffer
            while (queue.size() == capacity) {
                notFull.await();
            }

            queue.add(item);
            System.out.println("Added: " + item);

            // Signal consumers that buffer is not empty
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            // Wait until buffer has at least one item
            while (queue.isEmpty()) {
                notEmpty.await();
            }

            E item = queue.remove();
            System.out.println("Removed: " + item);

            // Signal producers that buffer is not full
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    // Signal all waiting threads
    public void clear() {
        lock.lock();
        try {
            queue.clear();
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Using await with timeout
    public E poll(long timeout) throws InterruptedException {
        lock.lock();
        try {
            long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(timeout);
            while (queue.isEmpty()) {
                if (nanos <= 0L) {
                    return null; // Timed out
                }
                nanos = notEmpty.awaitNanos(nanos);
            }

            E item = queue.remove();
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

}
