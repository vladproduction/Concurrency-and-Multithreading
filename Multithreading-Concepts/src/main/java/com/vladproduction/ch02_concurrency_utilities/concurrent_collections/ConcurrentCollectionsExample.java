package com.vladproduction.ch02_concurrency_utilities.concurrent_collections;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentCollectionsExample {
    public static void main(String[] args) throws InterruptedException {
        // 1. ConcurrentHashMap vs HashMap
        demonstrateConcurrentHashMap();

        // 2. CopyOnWriteArrayList
        demonstrateCopyOnWriteArrayList();

        // 3. BlockingQueue implementations
        demonstrateBlockingQueues();
    }

    private static void demonstrateConcurrentHashMap() throws InterruptedException {
        System.out.println("\n=== ConcurrentHashMap Demonstration ===");

        // Standard HashMap (not thread-safe)
        Map<String, Integer> regularMap = new HashMap<>();

        // Thread-safe ConcurrentHashMap
        ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        // Set up a task that adds entries to the map
        Runnable mapUpdateTask = () -> {
            try {
                for (int i = 0; i < 100; i++) {
                    String key = "key" + i + "-" + Thread.currentThread().getId();

                    // Update both maps
                    regularMap.put(key, i);  // Not thread-safe
                    concurrentMap.put(key, i); // Thread-safe

                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Create and start multiple threads
        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(mapUpdateTask);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Check results
        System.out.println("Expected entries: " + (100 * threadCount));
        System.out.println("HashMap size: " + regularMap.size() +
                " (might be inconsistent due to race conditions)");
        System.out.println("ConcurrentHashMap size: " + concurrentMap.size());

        // Demonstrate atomic operations on ConcurrentHashMap
        System.out.println("\nConcurrentHashMap atomic operations:");

        // Initialize counter
        concurrentMap.put("counter", 0);

        // Multiple threads incrementing counter
        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                // Thread-safe atomic update
                concurrentMap.compute("counter", (key, value) -> value + 1);
            }
        };

        Thread[] incrementThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            incrementThreads[i] = new Thread(incrementTask);
            incrementThreads[i].start();
        }

        // Wait for completion
        for (Thread t : incrementThreads) {
            t.join();
        }

        System.out.println("Final counter value: " + concurrentMap.get("counter") +
                " (should be 5000)");

        // Demonstrate other useful methods
        concurrentMap.putIfAbsent("uniqueKey", 42);  // Only adds if key not present
        concurrentMap.replace("counter", concurrentMap.get("counter"), 0);  // CAS operation

        System.out.println("After updates:");
        System.out.println("- uniqueKey: " + concurrentMap.get("uniqueKey"));
        System.out.println("- counter: " + concurrentMap.get("counter"));
    }

    private static void demonstrateCopyOnWriteArrayList() throws InterruptedException {
        System.out.println("\n=== CopyOnWriteArrayList Demonstration ===");

        // Standard ArrayList (not thread-safe)
        List<String> regularList = new ArrayList<>();

        // Thread-safe CopyOnWriteArrayList
        CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();

        // Fill lists with initial data
        for (int i = 0; i < 10; i++) {
            String item = "Item-" + i;
            regularList.add(item);
            copyOnWriteList.add(item);
        }

        // Create a task that iterates and modifies the list
        Runnable iterateAndModifyTask = () -> {
            String threadName = Thread.currentThread().getName();

            try {
                // 1. Iterate and try to modify regular ArrayList
                try {
                    for (String item : regularList) {
                        System.out.println(threadName + " reading: " + item);

                        // This will likely throw ConcurrentModificationException
                        if (item.endsWith("5")) {
                            regularList.add("New-" + System.currentTimeMillis());
                        }

                        Thread.sleep(10);
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println(threadName +
                            " got ConcurrentModificationException on ArrayList");
                }

                // 2. Iterate and modify CopyOnWriteArrayList
                // No exception will be thrown, but iterator won't see new elements
                for (String item : copyOnWriteList) {
                    System.out.println(threadName + " safely reading: " + item);

                    // This won't cause an exception, but iterator won't see the new element
                    if (item.endsWith("8")) {
                        copyOnWriteList.add("CoW-" + System.currentTimeMillis());
                        System.out.println(threadName + " added item to CopyOnWriteArrayList");
                    }

                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Create reader thread
        Thread reader = new Thread(iterateAndModifyTask, "ReaderThread");

        // Create modifier thread
        Thread modifier = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(50);

                    // Add to both lists
                    regularList.add("Dynamic-" + i);
                    copyOnWriteList.add("Dynamic-" + i);

                    System.out.println("ModifierThread added elements");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ModifierThread");

        // Start threads
        reader.start();
        modifier.start();

        // Wait for completion
        reader.join();
        modifier.join();

        // Check final state
        System.out.println("\nFinal list sizes:");
        System.out.println("ArrayList size: " + regularList.size());
        System.out.println("CopyOnWriteArrayList size: " + copyOnWriteList.size());
    }

    private static void demonstrateBlockingQueues() throws InterruptedException {
        System.out.println("\n=== BlockingQueue Implementations Demonstration ===");

        // 1. ArrayBlockingQueue - bounded, array-backed queue
        BlockingQueue<String> arrayQueue = new ArrayBlockingQueue<>(5);

        // 2. LinkedBlockingQueue - optionally bounded, linked-node queue
        BlockingQueue<String> linkedQueue = new LinkedBlockingQueue<>(10);

        // 3. PriorityBlockingQueue - unbounded priority queue
        BlockingQueue<Integer> priorityQueue = new PriorityBlockingQueue<>();

        // 4. DelayQueue - delay elements until their delay expires
        DelayQueue<DelayedElement> delayQueue = new DelayQueue<>();

        // 5. SynchronousQueue - handoff queue with no internal capacity
        BlockingQueue<String> syncQueue = new SynchronousQueue<>();

        // Producer task for the blocking queues
        Thread producer = new Thread(() -> {
            try {
                // ArrayBlockingQueue demonstration
                System.out.println("Producer: Adding to ArrayBlockingQueue");
                for (int i = 0; i < 7; i++) {
                    String item = "Item-" + i;

                    if (i >= 5) {
                        System.out.println("Producer: Queue full, will block until space available");
                    }

                    arrayQueue.put(item); // Will block if queue is full
                    System.out.println("Producer: Added " + item);
                    Thread.sleep(100);
                }

                // Adding delayed elements
                System.out.println("\nProducer: Adding to DelayQueue");
                for (int i = 1; i <= 3; i++) {
                    DelayedElement element = new DelayedElement("Delayed-" + i, i * 1000);
                    delayQueue.put(element);
                    System.out.println("Added " + element +
                            " (will be available after " + i + " seconds)");
                }

                // SynchronousQueue handoff demonstration
                System.out.println("\nProducer: Attempting to add to SynchronousQueue");
                System.out.println("Producer: Will block until consumer is ready to receive");
                syncQueue.put("Handoff-Item");
                System.out.println("Producer: Handoff successful");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ProducerThread");

        // Consumer task
        Thread consumer = new Thread(() -> {
            try {
                // Give producer head start
                Thread.sleep(300);

                // ArrayBlockingQueue consumption
                System.out.println("\nConsumer: Taking from ArrayBlockingQueue");
                for (int i = 0; i < 7; i++) {
                    String item = arrayQueue.take(); // Will block if queue is empty
                    System.out.println("Consumer: Received " + item);
                    Thread.sleep(300);
                }

                // DelayQueue consumption
                System.out.println("\nConsumer: Taking from DelayQueue");
                for (int i = 0; i < 3; i++) {
                    DelayedElement element = delayQueue.take(); // Will wait until delay expires
                    System.out.println("Consumer: Got " + element +
                            " after its delay expired");
                }

                // SynchronousQueue handoff demonstration
                System.out.println("\nConsumer: Ready to take from SynchronousQueue");
                Thread.sleep(1000); // Wait a bit to show the producer is waiting
                String handoff = syncQueue.take();
                System.out.println("Consumer: Received handoff item: " + handoff);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConsumerThread");

        // Priority queue demonstration in separate threads
        Thread priorityProducer = new Thread(() -> {
            try {
                // Add items in non-priority order
                System.out.println("\nAdding to PriorityBlockingQueue in mixed order:");
                int[] priorities = {5, 1, 10, 3, 2, 8};

                for (int p : priorities) {
                    priorityQueue.put(p);
                    System.out.println("Added item with priority: " + p);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PriorityProducerThread");

        Thread priorityConsumer = new Thread(() -> {
            try {
                Thread.sleep(1000); // Give producer time to add items

                System.out.println("\nTaking from PriorityBlockingQueue (should be in priority order):");
                while (!priorityQueue.isEmpty()) {
                    Integer item = priorityQueue.take();
                    System.out.println("Got item with priority: " + item);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PriorityConsumerThread");

        // Start all threads
        producer.start();
        consumer.start();
        priorityProducer.start();
        priorityConsumer.start();

        // Wait for all threads to complete
        producer.join();
        consumer.join();
        priorityProducer.join();
        priorityConsumer.join();
    }

    // Helper class for DelayQueue demonstration
    static class DelayedElement implements Delayed {
        private final String name;
        private final long availableTime;

        public DelayedElement(String name, long delayMillis) {
            this.name = name;
            this.availableTime = System.currentTimeMillis() + delayMillis;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = availableTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(getDelay(TimeUnit.MILLISECONDS),
                    other.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
