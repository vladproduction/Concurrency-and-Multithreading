package com.vladproduction.ch02_concurrency_utilities.synchronizers;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerExample {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Producer thread
        executor.submit(() -> {
            try {
                String data = "Data from producer";
                System.out.println("Producer has: " + data);

                // Exchange data with consumer
                String receivedData = exchanger.exchange(data);

                System.out.println("Producer received: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        executor.submit(() -> {
            try {
                String data = "Acknowledgment from consumer";

                // Simulate some processing time
                Thread.sleep(2000);
                System.out.println("Consumer has: " + data);

                // Exchange data with producer
                String receivedData = exchanger.exchange(data);

                System.out.println("Consumer received: " + receivedData);
                System.out.println("Consumer processing the data: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
    }

}
