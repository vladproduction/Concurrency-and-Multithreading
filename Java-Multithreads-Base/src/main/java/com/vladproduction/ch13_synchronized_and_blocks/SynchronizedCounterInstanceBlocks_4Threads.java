package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Demonstrates instance-based synchronization where each Counter object
 * uses its own instance (this) as the monitor for thread synchronization.
 * Shows that both synchronized methods and synchronized(this) blocks
 * provide equivalent protection.
 */
public class SynchronizedCounterInstanceBlocks_4Threads {

    private static final int INCREMENT_AMOUNT_FIRST_COUNTER = 1000;
    private static final int INCREMENT_AMOUNT_SECOND_COUNTER = 1000;

    public static void main(String[] args) {

        // Create two instances of counter class - each has its own independent lock
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        // Create two threads that will increment the first counter instance
        // Note: Both threads will synchronize on the same counter1 instance
        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER, i-> counter1.increment());
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER, i-> counter1.increment());

        // Create two threads that will increment the second counter instance
        // Note: Both threads will synchronize on the same counter2 instance
        Thread thirdThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER, i-> counter2.incrementWithLock());
        Thread fourthThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER, i-> counter2.incrementWithLock());

        // Start all threads
        threadStarter(firstThread, secondThread, thirdThread, fourthThread);

        // Wait for all threads to complete
        threadJoiner(firstThread, secondThread, thirdThread, fourthThread);

        // Print final counter values
        System.out.println("First counter: " + counter1.getCounter());  // Should be 2000
        System.out.println("Second counter: " + counter2.getCounter()); // Should be 2000
    }

    /**
     * Counter class with thread-safe increment operations.
     * Demonstrates two equivalent approaches to instance-based synchronization.
     */
    private static class Counter {
        private int counter = 0;

        /**
         * First synchronization approach - synchronized method.
         * Uses 'this' (the current instance) as the monitor.
         */
        public synchronized void increment() {
            this.counter++;
        }

        /**
         * Second synchronization approach - synchronized block.
         * Also uses 'this' as the monitor, making it functionally
         * equivalent to the synchronized method approach.
         */
        public void incrementWithLock() {
            synchronized (this) {
                this.counter++;
            }
        }

        /**
         * Thread-safe getter for the counter value
         */
        public synchronized int getCounter() {
            return counter;
        }
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

    /**
     * This example effectively demonstrates that:
     *
     * Non-static synchronized methods use the instance (this) as the monitor
     * Both synchronized methods and synchronized(this) blocks are equivalent
     * Different instances have independent locks, allowing concurrent execution
     * Threads accessing the same instance will properly synchronize
     *
     * This is a good educational example showing how instance-based synchronization works in Java,
     * particularly the distinction between class-level synchronization (from previous examples)
     * and instance-level synchronization shown here.
     * */

}
