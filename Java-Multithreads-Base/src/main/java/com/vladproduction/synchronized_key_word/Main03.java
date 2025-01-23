package com.vladproduction.synchronized_key_word;

import java.util.Arrays;
import java.util.function.IntConsumer;

import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public class Main03 {

    public static int firstCounter = 0;
    public static int secondCounter = 0;
    public static final int INCREMENT_AMOUNT_FIRST_COUNTER = 500000;
    public static final int INCREMENT_AMOUNT_SECOND_COUNTER = 600000;


    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER,
                i-> incrementFirstCounter());
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER,
                i-> incrementFirstCounter());
        Thread thirdThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER,
                i->incrementSecondCounter());
        Thread fourthThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER,
                i->incrementSecondCounter());

        startThreads(firstThread, secondThread, thirdThread, fourthThread);

        waitUntilAreCompleted(firstThread, secondThread, thirdThread, fourthThread);

        out.println(firstCounter);
        out.println(secondCounter);
    }

    private static Thread createIncrementingCounterThread(final int incrementAmount,
                                                          IntConsumer incrementingOperation){
        Thread thread = new Thread(()->{
            range(0, incrementAmount).forEach(incrementingOperation);
        });
        return thread;
    }

    private static void waitUntilAreCompleted(Thread... threads){
        Arrays.stream(threads).forEach(thread -> {
            try{
                thread.join();
            }catch (InterruptedException interruptedException){
                Thread.currentThread().interrupt();
            }
        });
    }

    private static void startThreads(Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);

    }

    private static synchronized void incrementFirstCounter(){
        firstCounter++;
    }
    private static synchronized void incrementSecondCounter(){
        secondCounter++;
    }
}
