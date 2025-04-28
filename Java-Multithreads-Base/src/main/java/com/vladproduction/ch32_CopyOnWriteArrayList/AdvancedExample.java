package com.vladproduction.ch32_CopyOnWriteArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AdvancedExample {

    private static final int READER_THREADS = 3;
    private static final int WRITER_THREADS = 2;
    private static final int OPERATIONS_PER_THREAD = 5;

    // Helper method for formatted logs with timestamps
    private static synchronized void log(String thread, String action, String message) {
        String timestamp = new SimpleDateFormat("[HH:mm:ss.SSS]").format(new Date());
        System.out.printf("%-15s %-15s %-20s %s%n", timestamp, "[" + thread + "]", action, message);
    }

    public static void main(String[] args) throws InterruptedException {
        log("MAIN", "INIT", "=== Starting Advanced CopyOnWriteArrayList Example ===");

        // Create a CopyOnWriteArrayList with initial values
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add("Initial-" + i);
        }
        log("MAIN", "INIT", "Created list with initial values: " + list);

        // Use CountDownLatch to synchronize thread completion
        CountDownLatch latch = new CountDownLatch(READER_THREADS + WRITER_THREADS);

        // Thread pools for readers and writers
        ExecutorService readerPool = Executors.newFixedThreadPool(READER_THREADS);
        ExecutorService writerPool = Executors.newFixedThreadPool(WRITER_THREADS);

        // Track the number of copies created
        AtomicInteger copyCount = new AtomicInteger(0);

        log("MAIN", "SETUP", "Starting " + WRITER_THREADS + " writer threads and " +
                READER_THREADS + " reader threads");

        // Start writer threads
        for (int i = 0; i < WRITER_THREADS; i++) {
            final int writerIndex = i;
            final String writerName = "Writer-" + writerIndex;

            writerPool.submit(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        // Perform random write operations
                        int operation = random.nextInt(3);

                        switch (operation) {
                            case 0: // Add
                                String newValue = "W" + writerIndex + "-Val" + j;
                                int sizeBefore = list.size();
                                list.add(newValue);

                                log(writerName, "ADD", String.format(
                                        "Added: %s (size: %d → %d)", newValue, sizeBefore, list.size()));

                                copyCount.incrementAndGet();
                                break;

                            case 1: // Remove (if not empty)
                                if (!list.isEmpty()) {
                                    int indexToRemove = random.nextInt(list.size());
                                    String removed = list.remove(indexToRemove);

                                    log(writerName, "REMOVE", String.format(
                                            "Removed index %d: %s (size: %d → %d)",
                                            indexToRemove, removed, list.size()+1, list.size()));

                                    copyCount.incrementAndGet();
                                } else {
                                    log(writerName, "REMOVE", "Skipped - list is empty");
                                }
                                break;

                            case 2: // Update (if not empty)
                                if (!list.isEmpty()) {
                                    int indexToUpdate = random.nextInt(list.size());
                                    String oldValue = list.get(indexToUpdate);
                                    String updated = "W" + writerIndex + "-Upd" + j;
                                    list.set(indexToUpdate, updated);

                                    log(writerName, "UPDATE", String.format(
                                            "Updated index %d: %s → %s", indexToUpdate, oldValue, updated));

                                    copyCount.incrementAndGet();
                                } else {
                                    log(writerName, "UPDATE", "Skipped - list is empty");
                                }
                                break;
                        }
                        Thread.sleep(50 + random.nextInt(100));
                    }
                    log(writerName, "COMPLETE", "Finished all operations");

                } catch (Exception e) {
                    log(writerName, "ERROR", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Start reader threads
        for (int i = 0; i < READER_THREADS; i++) {
            final int readerIndex = i;
            final String readerName = "Reader-" + readerIndex;

            readerPool.submit(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        // Choose between different read operations
                        int operation = random.nextInt(3);

                        switch (operation) {
                            case 0: // Regular iteration
                                log(readerName, "ITERATE", "Starting iteration " + j);
                                StringBuilder snapshot = new StringBuilder("[");
                                int elementCount = 0;

                                for (String element : list) {
                                    if (elementCount > 0) snapshot.append(", ");
                                    snapshot.append(element);
                                    elementCount++;
                                    Thread.sleep(10);  // Slow reader to demonstrate snapshot view
                                }
                                snapshot.append("]");

                                log(readerName, "SNAPSHOT", String.format(
                                        "Completed iteration with %d elements: %s", elementCount, snapshot));
                                break;

                            case 1: // Iterator with explicit removal (which will fail)
                                try {
                                    log(readerName, "ITERATOR", "Attempting iterator.remove() (should fail)");
                                    Iterator<String> iterator = list.iterator();
                                    if (iterator.hasNext()) {
                                        String element = iterator.next();
                                        iterator.remove(); // This will throw UnsupportedOperationException
                                        log(readerName, "ERROR", "Unexpectedly succeeded removing: " + element);
                                    } else {
                                        log(readerName, "ITERATOR", "List is empty, skipped removal test");
                                    }
                                } catch (UnsupportedOperationException e) {
                                    log(readerName, "EXPECTED", "Got expected exception: Cannot remove via iterator");
                                }
                                break;

                            case 2: // Check size and get random element
                                int size = list.size();
                                log(readerName, "SIZE", "Current size: " + size);

                                if (size > 0) {
                                    int randomIndex = random.nextInt(size);
                                    String element = list.get(randomIndex);
                                    log(readerName, "GET", String.format(
                                            "Element at index %d: %s", randomIndex, element));
                                } else {
                                    log(readerName, "GET", "List is empty, cannot get element");
                                }
                                break;
                        }
                        Thread.sleep(50 + random.nextInt(100));
                    }
                    log(readerName, "COMPLETE", "Finished all operations");

                } catch (Exception e) {
                    log(readerName, "ERROR", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        log("MAIN", "WAITING", "Waiting for all threads to complete...");
        latch.await();

        // Shutdown thread pools
        readerPool.shutdown();
        writerPool.shutdown();

        // Final status
        log("MAIN", "RESULT", "=== All threads completed ===");
        log("MAIN", "SUMMARY", "Number of array copies created: " + copyCount.get());
        log("MAIN", "SUMMARY", "Final list size: " + list.size() + " elements");

        StringBuilder finalContent = new StringBuilder();
        for (String element : list) {
            finalContent.append("\n  - ").append(element);
        }
        log("MAIN", "CONTENT", "Final list contents:" + finalContent);

        // Demo subList (non-structural modification)
        if (list.size() >= 2) {
            List<String> subList = list.subList(0, Math.min(2, list.size()));
            log("MAIN", "SUBLIST", "First 2 elements as subList: " + subList);
        }

        log("MAIN", "END", "Example completed successfully");
    }

}
