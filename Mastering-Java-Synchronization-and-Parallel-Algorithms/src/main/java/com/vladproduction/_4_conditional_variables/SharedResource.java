package com.vladproduction._4_conditional_variables;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private int data;
    private boolean available = false; // Condition to signal availability
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // Producer method
    public void produce(int value) {
        lock.lock();
        try {
            while (available) {
                // Wait until the data is consumed
                condition.await();
            }
            // Produce data
            data = value;
            System.out.println("Produced: " + data);
            available = true;
            // Signal that data is available
            condition.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    // Consumer method
    public void consume() {
        lock.lock();
        try {
            while (!available) {
                // Wait until there is data to consume
                condition.await();
            }
            // Consume data
            System.out.println("Consumed: " + data);
            available = false;
            // Signal that data has been consumed
            condition.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
