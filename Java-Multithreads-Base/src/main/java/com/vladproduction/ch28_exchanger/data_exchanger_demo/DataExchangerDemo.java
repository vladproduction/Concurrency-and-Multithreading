package com.vladproduction.ch28_exchanger.data_exchanger_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class demonstrates using Exchanger to facilitate data exchange between two threads.
 * The scenario simulates a producer collecting data and a processor analyzing data,
 * with periodic exchanges of filled/empty buffers.
 */
public class DataExchangerDemo {

    public static void main(String[] args) {
        // Create an exchanger for lists of strings (our data buffer)
        Exchanger<List<String>> exchanger = new Exchanger<>();

        // Create two buffer lists
        List<String> producerBuffer = new ArrayList<>();
        List<String> consumerBuffer = new ArrayList<>();

        // Create executor service
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Start the producer thread
        executor.execute(() -> {
            try {
                List<String> currentBuffer = producerBuffer;

                // Simulate producing data in multiple batches
                for (int i = 0; i < 5; i++) {
                    // Fill the buffer with data
                    fillBuffer(currentBuffer, i);

                    System.out.println("Producer: Buffer " + i + " filled with " +
                            currentBuffer.size() + " items. Exchanging...");

                    // Exchange the filled buffer for an empty one
                    // This will block until the consumer arrives at the exchange point
                    currentBuffer = exchanger.exchange(currentBuffer);

                    System.out.println("Producer: Received empty buffer from consumer. Size: " +
                            currentBuffer.size());

                    // Sleep a bit before next production cycle
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
                }

                // Add termination signal
                currentBuffer.add("EOD"); // End of Data marker
                exchanger.exchange(currentBuffer);

                System.out.println("Producer: Finished all production cycles.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer was interrupted");
            }
        });

        // Start the consumer thread
        executor.execute(() -> {
            try {
                List<String> currentBuffer = consumerBuffer;
                boolean receivedEOD = false;

                while (!receivedEOD) {
                    System.out.println("Consumer: Waiting to receive a filled buffer...");

                    // Exchange an empty buffer for a filled one
                    // This will block until the producer arrives at the exchange point
                    currentBuffer = exchanger.exchange(currentBuffer);

                    System.out.println("Consumer: Received buffer with " + currentBuffer.size() + " items.");

                    // Process the buffer
                    receivedEOD = processBuffer(currentBuffer);

                    // Clear the buffer for the next exchange
                    currentBuffer.clear();

                    System.out.println("Consumer: Cleared buffer for next exchange.");

                    // Sleep a bit before next consumption cycle
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                }

                System.out.println("Consumer: Finished processing all data.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer was interrupted");
            }
        });

        // Shutdown executor gracefully
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.err.println("Timeout occurred. Forcing shutdown.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Fills a buffer with simulated data items
     */
    private static void fillBuffer(List<String> buffer, int batchNumber) throws InterruptedException {
        buffer.clear(); // Clear any existing items

        // Generate between 5-10 data items
        int itemCount = ThreadLocalRandom.current().nextInt(5, 11);

        for (int i = 0; i < itemCount; i++) {
            // Simulate the time to produce an item
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));

            String dataItem = "Batch-" + batchNumber + "-Item-" + i;
            buffer.add(dataItem);
            System.out.println("Producer: Created " + dataItem);
        }
    }

    /**
     * Processes items in the buffer and returns true if EOD marker found
     */
    private static boolean processBuffer(List<String> buffer) throws InterruptedException {
        boolean foundEOD = false;

        for (String item : buffer) {
            if ("EOD".equals(item)) {
                System.out.println("Consumer: Found end-of-data marker.");
                foundEOD = true;
                continue;
            }

            // Simulate the time to process an item
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 500));
            System.out.println("Consumer: Processed " + item);
        }

        return foundEOD;
    }

}
