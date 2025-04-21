package com.vladproduction.ch01_core_concepts.thread_communication;

// Example 2: Proper Handling of Spurious Wakeups
public class SpuriousWakeupSafeExample {

    static class SharedResource {
        private boolean dataReady = false;
        private String data = null;

        // Producer method to set data
        public synchronized void setData(String data) {
            this.data = data;
            this.dataReady = true;
            System.out.println(Thread.currentThread().getName() +
                    " set data: " + data);

            // Wake up all waiting threads
            notifyAll();
        }

        // Consumer method to get data
        public synchronized String getData() throws InterruptedException {
            // Important: Using while instead of if to handle spurious wakeups
            while (!dataReady) {
                System.out.println(Thread.currentThread().getName() +
                        " waiting for data...");
                wait(); // Could wake up even if condition not met (spurious wakeup)
            }

            // Reset flag for next round
            dataReady = false;
            System.out.println(Thread.currentThread().getName() +
                    " got data: " + data);
            return data;
        }
    }

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Consumer threads
        Thread consumer1 = new Thread(() -> {
            try {
                String result = resource.getData();
                System.out.println("Consumer 1 working with: " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-1");

        Thread consumer2 = new Thread(() -> {
            try {
                String result = resource.getData();
                System.out.println("Consumer 2 working with: " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-2");

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                // Give consumers time to start waiting
                Thread.sleep(1000);
                resource.setData("Important Message");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Start the threads
        consumer1.start();
        consumer2.start();
        producer.start();

        // Wait for completion
        try {
            consumer1.join();
            consumer2.join();
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
