package com.vladproduction._4_conditional_variables;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Create producer and consumer threads
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);

        // Start the threads
        producer.start();
        consumer.start();

        try {
            // Wait for both threads to finish
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
    }
}
