package com.vladproduction.ch13_synchronized_and_blocks;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * For the demo, we will not use synchronized keyword in this example.
 * Assuming that the incrementFirstCounter and incrementSecondCounter methods are not synchronized,
 * race conditions can occur when multiple threads access these methods concurrently.
 * But here we have a scenario for each thread to increment the associated counter only.
 * So they don't cross each other.
 *
 * Note: Even without synchronization, when threads operate entirely on separate variables (with no shared state between them),
 * race conditions won't occur between those specific operations.
 * */
public class SynchronizedCounter {

    private static int firstCounter = 0;
    private static int secondCounter = 0;

    private static final int FIRST_THREAD_INCREMENT = 1000;
    private static final int SECOND_THREAD_INCREMENT = 1500;

    public static void main(String[] args) {

        Thread firstThread = createIncrementingCounterThread(FIRST_THREAD_INCREMENT, i-> incrementFirstCounter());
        firstThread.start();

        Thread secondThread = createIncrementingCounterThread(SECOND_THREAD_INCREMENT, i-> incrementSecondCounter());
        secondThread.start();

        try{
            firstThread.join();
            secondThread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println(firstCounter);
        System.out.println(secondCounter);

    }

    private static Thread createIncrementingCounterThread(int incrementAmount, IntConsumer incrementingOperation) {

        return new Thread(() -> {
            IntStream.range(0, incrementAmount).forEach(incrementingOperation);
        });

    }

    /**
     * The incrementFirstCounter and incrementSecondCounter methods are not synchronized.
     * This means that when multiple threads access these methods concurrently, race conditions can occur,
     * leading to incorrect counter values.
     * */
    private static void incrementFirstCounter() {
        firstCounter++;
    }

    private static void incrementSecondCounter() {
        secondCounter++;
    }


}
