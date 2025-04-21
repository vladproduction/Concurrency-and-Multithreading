package com.vladproduction.ch01_core_concepts.thread_communication;

// Example 1: Producer-Consumer Pattern with wait/notify
public class ProducerConsumerExample {

    static class Buffer {
        private final int[] data;
        private int putIndex = 0;
        private int getIndex = 0;
        private int count = 0;

        public Buffer(int size) {
            data = new int[size];
        }

        public synchronized void put(int value) throws InterruptedException {
            // Wait if buffer is full
            while (count == data.length) {
                System.out.println(Thread.currentThread().getName() +
                        " - Buffer full, waiting...");
                wait(); // Releases the lock and waits to be notified
            }

            // Add value to buffer
            data[putIndex] = value;
            System.out.println(Thread.currentThread().getName() +
                    " produced: " + value);

            // Update put index (circular buffer)
            putIndex = (putIndex + 1) % data.length;
            count++;

            // Notify waiting consumers
            notify(); // Wakes up one waiting thread

            // Optional: wake up all waiting threads
            // notifyAll();
        }

        public synchronized int get() throws InterruptedException {
            // Wait if buffer is empty
            while (count == 0) {
                System.out.println(Thread.currentThread().getName() +
                        " - Buffer empty, waiting...");
                wait(); // Releases the lock and waits to be notified
            }

            // Get value from buffer
            int value = data[getIndex];
            System.out.println(Thread.currentThread().getName() +
                    " consumed: " + value);

            // Update get index (circular buffer)
            getIndex = (getIndex + 1) % data.length;
            count--;

            // Notify waiting producers
            notify(); // Wakes up one waiting thread

            return value;
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer(5); // Shared buffer of size 5

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.put(i);
                    Thread.sleep((int)(Math.random() * 500)); // Random delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.get();
                    Thread.sleep((int)(Math.random() * 1000)); // Random delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        // Start the threads
        producer.start();
        consumer.start();

        // Wait for completion
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Producer-Consumer demonstration complete.");
    }

}
