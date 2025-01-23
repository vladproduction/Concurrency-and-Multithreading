package com.vladproduction.synchronized_key_word;

import java.util.Arrays;
import java.util.function.IntConsumer;

import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public class Main05 {

    public static int firstCounter = 0;
    public static int secondCounter = 0;
    public static final int INCREMENT_AMOUNT_FIRST_COUNTER = 500000;
    public static final int INCREMENT_AMOUNT_SECOND_COUNTER = 600000;
    public static final Object LOCK_TO_INCREMENT_FIRST_COUNTER = new Object();
    public static final Object LOCK_TO_INCREMENT_SECOND_COUNTER = new Object();


    public static void main(String[] args) throws InterruptedException {

        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();

        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER,
                i-> firstCounter.increment());
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER,
                i-> firstCounter.increment());
        Thread thirdThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER,
                i->secondCounter.increment());
        Thread fourthThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER,
                i->secondCounter.increment());

        startThreads(firstThread, secondThread, thirdThread, fourthThread);

        waitUntilAreCompleted(firstThread, secondThread, thirdThread, fourthThread);

        out.println(firstCounter.counter);
        out.println(secondCounter.counter);
    }

    public static final class Counter{
        private int counter;
        //variant_#1
//        public synchronized void increment(){
//            counter++;
//        }
        //variant_#2
        public void increment(){
            synchronized (this){
                counter++;
            }
        }
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

    private static void incrementFirstCounter(){
        synchronized (LOCK_TO_INCREMENT_FIRST_COUNTER){
            firstCounter++;
        }
    }
    private static void incrementSecondCounter(){
        synchronized (LOCK_TO_INCREMENT_SECOND_COUNTER){
            secondCounter++;
        }
    }
}
