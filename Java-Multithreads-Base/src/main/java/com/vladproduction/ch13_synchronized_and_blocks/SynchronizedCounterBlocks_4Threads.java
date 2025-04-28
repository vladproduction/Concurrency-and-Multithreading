package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Demonstrates fine-grained synchronization using separate lock objects.
 * This approach allows for better parallelism while maintaining thread safety,
 * as threads accessing different counters don't block each other.
 */
public class SynchronizedCounterBlocks_4Threads {

    // Shared counters that will be accessed by multiple threads
    private static int firstCounter = 0;
    private static int secondCounter = 0;

    // Number of increments each thread will perform
    private static final int INCREMENT_AMOUNT_FIRST_COUNTER = 1000;
    private static final int INCREMENT_AMOUNT_SECOND_COUNTER = 1000;

    // Separate lock objects for each counter to allow fine-grained synchronization
    private static final Object LOCK_FIRST_COUNTER = new Object();
    private static final Object LOCK_SECOND_COUNTER = new Object();

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
     * Increments the first counter with synchronization.
     * Uses LOCK_FIRST_COUNTER as the monitor, which means only threads
     * accessing the first counter will contend for this lock.
     * Threads accessing the second counter can run concurrently.
     */
    private static void incrementFirstCounter() {
        synchronized (LOCK_FIRST_COUNTER) {
            firstCounter++;
        }
    }

    /**
     * Increments the second counter with synchronization.
     * Uses LOCK_SECOND_COUNTER as the monitor, which means only threads
     * accessing the second counter will contend for this lock.
     * Threads accessing the first counter can run concurrently.
     */
    private static void incrementSecondCounter() {
        synchronized (LOCK_SECOND_COUNTER) {
            secondCounter++;
        }
    }

    /**
     * Utility method to start multiple threads
     */
    private static void threadStarter(Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }

    /**
     * Utility method to join multiple threads (wait for their completion)
     */
    private static void threadJoiner(Thread... threads){
        Arrays.stream(threads).forEach(threadToJoin -> {
            try{
                threadToJoin.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });
    }

}
