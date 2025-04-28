package com.vladproduction.ch11_threads_factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple ThreadFactory implementation that creates customized threads
 * with sequential naming, configurable priority, and daemon status.
 */
public class SimpleThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final int priority;
    private final boolean isDaemon;
    private final AtomicInteger threadCounter = new AtomicInteger(1);

    /**
     * Creates a thread factory with default settings:
     * - Normal priority
     * - Non-daemon threads
     * - "Worker" name prefix
     */
    public SimpleThreadFactory() {
        this("Worker", Thread.NORM_PRIORITY, false);
    }

    /**
     * Creates a thread factory with custom settings.
     *
     * @param namePrefix The prefix for thread names
     * @param priority The priority level (1-10)
     * @param isDaemon Whether threads should be daemon threads
     */
    public SimpleThreadFactory(String namePrefix, int priority, boolean isDaemon) {
        this.namePrefix = namePrefix;
        this.priority = validatePriority(priority);
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String threadName = namePrefix + "-" + threadCounter.getAndIncrement();
        Thread thread = new Thread(runnable, threadName);

        // Apply settings
        thread.setPriority(priority);
        thread.setDaemon(isDaemon);

        return thread;
    }

    private int validatePriority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            return Thread.MIN_PRIORITY;
        } else if (priority > Thread.MAX_PRIORITY) {
            return Thread.MAX_PRIORITY;
        }
        return priority;
    }
}
