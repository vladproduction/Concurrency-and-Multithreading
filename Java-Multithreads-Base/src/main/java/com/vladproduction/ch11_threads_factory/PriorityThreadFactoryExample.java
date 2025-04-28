package com.vladproduction.ch11_threads_factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates a more practical use of ThreadFactory to create threads
 * with different priorities for a task processing system.
 */
public class PriorityThreadFactoryExample {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting task processing simulation");

        // Create task queues
        BlockingQueue<Task> highPriorityTasks = new LinkedBlockingQueue<>();
        BlockingQueue<Task> normalPriorityTasks = new LinkedBlockingQueue<>();
        BlockingQueue<Task> lowPriorityTasks = new LinkedBlockingQueue<>();

        // Create thread factories with different priorities
        ThreadFactory highPriorityFactory = new PriorityThreadFactory("Critical", Thread.MAX_PRIORITY);
        ThreadFactory normalPriorityFactory = new PriorityThreadFactory("Normal", Thread.NORM_PRIORITY);
        ThreadFactory lowPriorityFactory = new PriorityThreadFactory("Background", Thread.MIN_PRIORITY);

        // Create task processors
        Thread highPriorityProcessor = highPriorityFactory.newThread(
                new TaskProcessor(highPriorityTasks, "High Priority"));
        Thread normalPriorityProcessor = normalPriorityFactory.newThread(
                new TaskProcessor(normalPriorityTasks, "Normal Priority"));
        Thread lowPriorityProcessor = lowPriorityFactory.newThread(
                new TaskProcessor(lowPriorityTasks, "Low Priority"));

        // Start all processors
        highPriorityProcessor.start();
        normalPriorityProcessor.start();
        lowPriorityProcessor.start();

        // Add tasks to queues
        System.out.println("Adding tasks to queues...");
        for (int i = 1; i <= 5; i++) {
            highPriorityTasks.add(new Task("Critical-" + i, 100));
            normalPriorityTasks.add(new Task("Normal-" + i, 100));
            lowPriorityTasks.add(new Task("Background-" + i, 100));
        }

        // Add poison pills to stop processors
        highPriorityTasks.add(Task.POISON);
        normalPriorityTasks.add(Task.POISON);
        lowPriorityTasks.add(Task.POISON);

        // Wait for all processors to finish
        highPriorityProcessor.join();
        normalPriorityProcessor.join();
        lowPriorityProcessor.join();

        System.out.println("All task processors have completed their work.");
    }

    /**
     * Represents a task to be processed.
     */
    static class Task {
        private final String name;
        private final long duration;

        // Special task used to signal thread termination
        public static final Task POISON = new Task("POISON", 0);

        public Task(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        public void execute() throws InterruptedException {
            System.out.println("[" + Thread.currentThread().getName() +
                    "] Processing task: " + name);
            TimeUnit.MILLISECONDS.sleep(duration);
            System.out.println("[" + Thread.currentThread().getName() +
                    "] Completed task: " + name);
        }

        public boolean isPoison() {
            return this == POISON;
        }
    }

    /**
     * Processes tasks from a queue until a poison pill is encountered.
     */
    static class TaskProcessor implements Runnable {
        private final BlockingQueue<Task> taskQueue;
        private final String processorType;

        public TaskProcessor(BlockingQueue<Task> taskQueue, String processorType) {
            this.taskQueue = taskQueue;
            this.processorType = processorType;
        }

        @Override
        public void run() {
            System.out.println("[" + Thread.currentThread().getName() +
                    "] " + processorType + " processor started");

            try {
                while (true) {
                    Task task = taskQueue.take();

                    if (task.isPoison()) {
                        System.out.println("[" + Thread.currentThread().getName() +
                                "] Received shutdown signal, stopping.");
                        break;
                    }

                    task.execute();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[" + Thread.currentThread().getName() +
                        "] Processor was interrupted");
            }

            System.out.println("[" + Thread.currentThread().getName() +
                    "] " + processorType + " processor terminated");
        }
    }

    /**
     * Factory for creating threads with specific priorities and naming patterns.
     */
    static class PriorityThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final int priority;
        private final AtomicInteger threadCount = new AtomicInteger(1);

        public PriorityThreadFactory(String namePrefix, int priority) {
            this.namePrefix = namePrefix;
            this.priority = validatePriority(priority);
        }

        private int validatePriority(int priority) {
            if (priority < Thread.MIN_PRIORITY) {
                return Thread.MIN_PRIORITY;
            } else if (priority > Thread.MAX_PRIORITY) {
                return Thread.MAX_PRIORITY;
            }
            return priority;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable,
                    namePrefix + "-Thread-" + threadCount.getAndIncrement());
            thread.setPriority(priority);

            // Log thread creation
            System.out.println("Created " + thread.getName() +
                    " with priority " + thread.getPriority());

            return thread;
        }
    }

}
