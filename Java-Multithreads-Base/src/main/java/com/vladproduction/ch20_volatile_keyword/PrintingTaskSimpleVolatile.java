package com.vladproduction.ch20_volatile_keyword;

import java.util.concurrent.TimeUnit;

/**
 * Without the volatile keyword, there would be a risk that the printing thread might never see the updated value
 * from the main thread due to thread visibility issues, potentially causing the printing to continue indefinitely.
 * */
public class PrintingTaskSimpleVolatile implements Runnable{

    /**
     * The volatile keyword here ensures that:
     *  1) Any thread reading the shouldPrint variable will see its most up-to-date value
     *  2) Changes to this variable by one thread are immediately visible to other threads
     * */

    private volatile boolean shouldPrint = true;

    public void setShouldPrint(boolean shouldPrint) {
        this.shouldPrint = shouldPrint;
    }

    @Override
    public void run() {
        try{
            while (shouldPrint){
                System.out.println("I`m working!");
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {


        PrintingTaskSimpleVolatile printingTaskSimpleVolatile = new PrintingTaskSimpleVolatile();
        Thread printingThread = new Thread(printingTaskSimpleVolatile);
        printingThread.start();

        TimeUnit.SECONDS.sleep(5);

        printingTaskSimpleVolatile.setShouldPrint(false);

        System.out.println("Printing should be stopped!");

    }

}
