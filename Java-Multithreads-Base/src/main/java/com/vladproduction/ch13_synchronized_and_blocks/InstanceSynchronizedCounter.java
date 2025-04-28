package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Demonstrates instance-based synchronization where the monitor is the instance of the object.
 * When non-static methods are synchronized, each instance has its own independent lock.
 * This allows multiple instances to execute synchronized methods concurrently.
 */
public class InstanceSynchronizedCounter {

    // Instance variables (not static) - each instance has its own copy
    private int counter = 0;

    // Custom lock object for fine-grained synchronization
    private final Object counterLock = new Object();

    public static void main(String[] args) {

        // Create two separate counter instances
        InstanceSynchronizedCounter counter1 = new InstanceSynchronizedCounter();
        InstanceSynchronizedCounter counter2 = new InstanceSynchronizedCounter();

        // Create threads operating on first counter instance
        Thread thread1 = counter1.createIncrementingThread(1000);
        Thread thread2 = counter1.createIncrementingThread(1000);

        // Create threads operating on second counter instance
        Thread thread3 = counter2.createIncrementingThread(1500);
        Thread thread4 = counter2.createIncrementingThread(1500);

        // Start all threads
        threadStarter(thread1, thread2, thread3, thread4);

        // Wait for all threads to complete
        threadJoiner(thread1, thread2, thread3, thread4);

        // Print results
        System.out.println("Counter 1: " + counter1.getCounter());  // Should be 2000
        System.out.println("Counter 2: " + counter2.getCounter());  // Should be 3000

    }

    /**
     * Creates a thread that will increment this instance's counter a given number of times
     */
    public Thread createIncrementingThread(int incrementAmount) {
        return new Thread(() -> {
            IntStream.range(0, incrementAmount).forEach(i -> increment());
        });
    }

    /**
     * Method 1: Using the synchronized keyword on the method.
     * When a non-static method is synchronized, it uses the current object instance (this)
     * as the monitor/lock. Only one thread can execute this method on the same instance at a time.
     * Different instances can execute in parallel.
     */
    public synchronized void increment() {
        counter++;
    }

    /**
     * Method 2: Alternative implementation using a synchronized block with 'this'.
     * This is functionally equivalent to the method above.
     */
    public void incrementWithThisLock() {
        synchronized (this) {
            counter++;
        }
    }

    /**
     * Method 3: Using a dedicated lock object for more fine-grained control.
     * This allows different synchronized sections to use different locks,
     * even within the same instance.
     */
    public void incrementWithCustomLock() {
        synchronized (counterLock) {
            counter++;
        }
    }

    /**
     * Synchronized getter to ensure visibility of the latest value
     */
    public synchronized int getCounter() {
        return counter;
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









