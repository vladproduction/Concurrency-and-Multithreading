package com.vladproduction.ch11_threads_factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

/**
 * Demonstrates using a ThreadFactory with uncaught exception handling.
 * ThreadFactory provides a way to customize thread creation,
 * while UncaughtExceptionHandler allows for centralized exception handling.
 */
public class ThreadFactoryExceptionHandlingExample {

    private static final String EXCEPTION_LOG_FORMAT = "Exception occurred: '%s' in thread '%s'\n";

    public static void main(String[] args) throws InterruptedException {
        // Define a reusable exception handler
        Thread.UncaughtExceptionHandler globalExceptionHandler = (thread, exception) -> {
            out.printf(EXCEPTION_LOG_FORMAT, exception.getMessage(), thread.getName());
        };

        // Create two different thread factories for comparison
        ThreadFactory daemonFactory = new CustomThreadFactory("Daemon-Worker", true, globalExceptionHandler);
        ThreadFactory normalFactory = new CustomThreadFactory("Regular-Worker", false, globalExceptionHandler);

        // Create and start threads that will throw exceptions
        Thread daemonThread1 = daemonFactory.newThread(new FailingTask("Database failure"));
        Thread daemonThread2 = daemonFactory.newThread(new FailingTask("Network timeout"));
        Thread normalThread = normalFactory.newThread(new FailingTask("Calculation error"));

        // Start all threads
        daemonThread1.start();
        daemonThread2.start();
        normalThread.start();

        // Wait for threads to complete (including exception handling)
        daemonThread1.join();
        daemonThread2.join();
        normalThread.join();

        out.println("All threads have completed execution or thrown exceptions.");

        // Example of successful task execution
        ThreadFactory workerFactory = new CustomThreadFactory("Worker", false, globalExceptionHandler);
        Thread workerThread = workerFactory.newThread(new SuccessfulTask("Processing completed"));
        workerThread.start();
        workerThread.join();
    }

    /**
     * Task that deliberately fails by throwing an exception.
     * Used to demonstrate exception handling.
     */
    public static final class FailingTask implements Runnable {
        private final String errorMessage;

        public FailingTask(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void run() {
            out.println("Starting task in thread: " + currentThread().getName());
            out.println("Thread is daemon: " + currentThread().isDaemon());

            // Simulate work before failing
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }

            // Throw exception to trigger the handler
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * Task that completes successfully.
     */
    public static final class SuccessfulTask implements Runnable {
        private final String message;

        public SuccessfulTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            out.println("Starting successful task in thread: " + currentThread().getName());

            // Simulate some work
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }

            out.println("Task completed successfully: " + message);
        }
    }

    /**
     * A custom ThreadFactory implementation that creates threads with:
     * - Custom naming pattern with sequential numbering
     * - Configurable daemon status
     * - Assigned uncaught exception handler
     */
    public static final class CustomThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final boolean daemon;
        private final Thread.UncaughtExceptionHandler exceptionHandler;
        private final AtomicInteger threadCounter = new AtomicInteger(1);

        /**
         * Creates a new thread factory with the specified configuration.
         *
         * @param namePrefix       Prefix for thread names created by this factory
         * @param daemon           Whether to create daemon threads
         * @param exceptionHandler Handler for uncaught exceptions in created threads
         */
        public CustomThreadFactory(String namePrefix, boolean daemon, Thread.UncaughtExceptionHandler exceptionHandler) {
            this.namePrefix = namePrefix;
            this.daemon = daemon;
            this.exceptionHandler = exceptionHandler;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            // Create thread with a meaningful name
            String threadName = namePrefix + "-" + threadCounter.getAndIncrement();
            Thread thread = new Thread(runnable, threadName);

            // Configure thread properties
            thread.setUncaughtExceptionHandler(exceptionHandler);
            thread.setDaemon(daemon);

            return thread;
        }
    }


}


