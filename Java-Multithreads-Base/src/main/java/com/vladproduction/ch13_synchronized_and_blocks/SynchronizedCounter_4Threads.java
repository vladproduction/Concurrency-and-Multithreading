package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;


/**
 * Demonstrates synchronization using static synchronized methods.
 * When methods are static synchronized, they use the Class object (SynchronizedCounter_4Threads.class)
 * as the monitor/lock. This means only one thread can execute ANY static synchronized method
 * in this class at a time, providing thread safety but limiting parallelism.
 */
public class SynchronizedCounter_4Threads {

    // Shared counters that will be accessed by multiple threads
    private static int firstCounter = 0;
    private static int secondCounter = 0;

    // Number of increments each thread will perform
    private static final int INCREMENT_AMOUNT_FIRST_COUNTER = 1000;
    private static final int INCREMENT_AMOUNT_SECOND_COUNTER = 1000;

    public static void main(String[] args) {
        // Create two threads that will increment the first counter
        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER, i-> incrementFirstCounter());
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER, i-> incrementFirstCounter());

        // Create two threads that will increment the second counter
        Thread thirdThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER, i-> incrementSecondCounter());
        Thread fourthThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER, i-> incrementSecondCounter());

        // Start all threads
        threadStarter(firstThread, secondThread, thirdThread, fourthThread);

        // Wait for all threads to complete
        threadJoiner(firstThread, secondThread, thirdThread, fourthThread);

        // Print final counter values
        System.out.println("First counter: " + firstCounter);  // Should be 2000
        System.out.println("Second counter: " + secondCounter); // Should be 2000
    }

    /**
     * Creates a thread that will perform the specified increment operation a given number of times
     */
    private static Thread createIncrementingCounterThread(int incrementAmount, IntConsumer incrementingOperation) {
        return new Thread(() -> {
            IntStream.range(0, incrementAmount).forEach(incrementingOperation);
        });
    }

    /**
     * Synchronized method for incrementing the first counter.
     * Since this is a static synchronized method, it uses the class object as the monitor.
     * This ensures that only one thread at a time can execute this method.
     */
    private static synchronized void incrementFirstCounter() {
        firstCounter++;
    }

    /**
     * Synchronized method for incrementing the second counter.
     * Since this is a static synchronized method, it also uses the class object as the monitor.
     * This means this method shares the same lock as incrementFirstCounter(),
     * so these methods cannot be executed concurrently by different threads.
     */
    private static synchronized void incrementSecondCounter() {
        secondCounter++;
    }

    /**
     * Utility method to start multiple threads
     */
    private static void threadStarter(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

    /**
     * Utility method to join multiple threads (wait for their completion)
     */
    private static void threadJoiner(Thread... threads) {
        Arrays.stream(threads).forEach(threadToJoin -> {
            try {
                threadToJoin.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
