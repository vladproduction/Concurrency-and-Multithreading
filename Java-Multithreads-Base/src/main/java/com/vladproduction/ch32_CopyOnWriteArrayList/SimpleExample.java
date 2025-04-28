package com.vladproduction.ch32_CopyOnWriteArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * CopyOnWriteArrayList is a thread-safe variant of ArrayList where all mutative operations (add, set, etc.)
 * create a fresh copy of the underlying array. This makes it ideal for scenarios where read operations outnumber write operations,
 * as reads don't require synchronization and thus perform very well.
 * */
public class SimpleExample {

    // Helper method for formatted logs with timestamps
    private static String getTimestamp() {
        return new SimpleDateFormat("[HH:mm:ss.SSS]").format(new Date());
    }

    private static void log(String source, String message) {
        System.out.printf("%-20s %-15s %s%n", getTimestamp(), "[" + source + "]", message);
    }

    public static void main(String[] args) throws InterruptedException {
        log("MAIN", "=== Starting Simple CopyOnWriteArrayList Example ===");

        // Create a CopyOnWriteArrayList
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        // Initial data
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        log("MAIN", "Initial list: " + list);
        log("MAIN", "=== Starting reader and writer threads ===");

        // Writer thread that adds elements
        Thread writerThread = new Thread(() -> {
            for (int i = 10; i < 15; i++) {
                list.add(i);
                log("WRITER", "Added: " + i + " → List: " + list);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log("WRITER", "Finished all write operations");
        }, "Writer");

        // Reader thread that iterates over the list
        Thread readerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                log("READER", "Starting iteration " + (i+1) + "...");
                StringBuilder snapshot = new StringBuilder();
                for (Integer number : list) {
                    snapshot.append(number).append(" ");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log("READER", "Iteration " + (i+1) + " snapshot: " + snapshot);
            }
            log("READER", "Finished all read operations");
        }, "Reader");

        // Start both threads
        writerThread.start();
        Thread.sleep(10); // Tiny delay so reader starts slightly after writer
        readerThread.start();

        // Wait for both threads to complete
        writerThread.join();
        readerThread.join();

        // Print final list contents
        log("MAIN", "=== All threads completed ===");
        log("MAIN", "Final list contents: " + list);
        log("MAIN", "Example completed successfully");
    }

    /*public static void main(String[] args) {

        // Create a CopyOnWriteArrayList
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        //initial data:
        for (int i = 0; i < 10; i++) {
            list .add(i);
        }

        // Writer thread that adds elements
        Thread writer = new Thread(() -> {
            for (int i = 10; i < 15; i++) {
                list.add(i);
                System.out.println("Writer add: " + i);
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

            }
        });

        // Reader thread that reads elements
        Thread reader = new Thread(() -> {
            // This iteration won't throw ConcurrentModificationException
            // even when writer is modifying the list
            for (int i = 0; i < 3; i++) {
                System.out.println("Reader iteration " + (i + 1) + ":");
                for (Integer number : list) {
                    System.out.print(number + " ");
                    try {
                        TimeUnit.MILLISECONDS.sleep(150);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("\n");
            }
        });

        writer.start();
        reader.start();

        try {
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final list content:");
        for (Integer i : list) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("\n------------------\nMain thread: Program completed.");

    }*/
}
