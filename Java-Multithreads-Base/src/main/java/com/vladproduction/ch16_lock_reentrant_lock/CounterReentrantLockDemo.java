package com.vladproduction.ch16_lock_reentrant_lock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * This code demonstrates thread synchronization using a safely manipulate a counter from multiple threads. `ReentrantLock`
 * Since both threads perform an equal number of increments and decrements (10 each), the final counter-value should be 0 if everything works correctly.
 * This is a good example of using for thread synchronization in Java, demonstrating proper locking patterns and thread management. `ReentrantLock`
 * */
public class CounterReentrantLockDemo {
    public static void main(String[] args) {

        Counter counter = new Counter();

        //adjustable number of amounts to increment the counter
        int incrementAmount = 10;

        //create the thread responsible for incrementing:
        Thread incrementingThread = new Thread(createTaskDoingOperationsOnCounter(counter, i -> counter.increment(), incrementAmount));

        //adjustable number of amounts to decrement the counter
        int decrementAmount = 10;

        //create the thread responsible for decrementing:
        Thread decrementingThread = new Thread(createTaskDoingOperationsOnCounter(counter, i -> counter.decrement(), decrementAmount));

        //starting threads method
        starterThreads(incrementingThread, decrementingThread);

        //waiting for threads to complete
        joinerThreads(incrementingThread, decrementingThread);

        //printing result:
        System.out.printf("Counter value: %d\n", counter.getValue());


    }

    private static void joinerThreads(Thread... threads) {
        Arrays.stream(threads).forEach(threadToJoin -> {
            try{
                threadToJoin.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });
    }

    private static void starterThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }


    private static Runnable createTaskDoingOperationsOnCounter(Counter counter, IntConsumer operation, int times){
        return () -> {
            counter.lock();
            try{
                IntStream.range(0, times).forEach(operation);
            }finally {
                counter.unlock();
            }
        };
    }

    private static final class Counter{

        private final Lock lock = new ReentrantLock();
        private int value;

        public void lock(){
            lock.lock();
            printCurrentThreadNameArgument("Thread %s locked counter.\n");
        }

        public void increment(){
            value++;
            printCurrentThreadNameArgument("Thread %s incremented counter.\n");
        }

        public void decrement(){
            value--;
            printCurrentThreadNameArgument("Thread %s decremented counter.\n");
        }

        public void unlock(){
            printCurrentThreadNameArgument("Thread %s unlocked counter.\n");
            lock.unlock();
        }

        public int getValue() {
            return value;
        }

        private static void printCurrentThreadNameArgument(String message){
            System.out.printf(message, Thread.currentThread().getName());

        }
    }

}
