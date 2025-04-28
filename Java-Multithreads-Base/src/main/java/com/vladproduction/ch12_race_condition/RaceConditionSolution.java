package com.vladproduction.ch12_race_condition;

import java.util.stream.IntStream;

public class RaceConditionSolution {

    //common counter
    private static int counter = 0;

    //number of increments for each thread to execute
    private static final int FIRST_THREAD_INCREMENT = 1000;
    private static final int SECOND_THREAD_INCREMENT = 1500;

    public static void main(String[] args) {

        Thread firstThread = createIncrementingCounterThread(FIRST_THREAD_INCREMENT);

        Thread secondThread = createIncrementingCounterThread(SECOND_THREAD_INCREMENT);

        firstThread.start();
        secondThread.start();

        try{
            firstThread.join();
            secondThread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println(counter);

    }

    private static Thread createIncrementingCounterThread(int incrementAmount) {
        return new Thread(() -> {
            IntStream.range(0, incrementAmount).forEach(i -> incrementCounter());
        });
    }

    private static synchronized void incrementCounter() {
        counter++;
    }

}
