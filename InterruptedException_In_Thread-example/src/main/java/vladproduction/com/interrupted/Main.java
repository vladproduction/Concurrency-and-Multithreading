package com.vladproduction.interrupted;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)throws InterruptedException {

        System.out.println("\n=======MAIN-START: " +  Thread.currentThread().getName());

        CountDownLatch mainCDL = new CountDownLatch(1);
        CountDownLatch spareCDL = new CountDownLatch(1);

        Runnable runnable = () -> {
            System.out.printf("\nStatus of RUNNABLE (isInterrupted): %s%n",Thread.currentThread().isInterrupted());

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("\trunnable{: " + Thread.currentThread().getName());
                    spareCDL.countDown();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    System.out.printf("InterruptedException, name: %s, status: %s%n",
                            Thread.currentThread().getName(),
                            Thread.currentThread().isInterrupted());

                    Thread.currentThread().interrupt(); //flag changed
                    System.out.println("--------status now?---------");
                    System.out.printf("InterruptedException, name: %s, status: %s%n",
                            Thread.currentThread().getName(),
                            Thread.currentThread().isInterrupted());
                }
            }
            System.out.println("\trunnable: " + Thread.currentThread().getName() + "}");
            mainCDL.countDown();
        };

        Thread thread = new Thread(runnable);
        System.out.println("\nRUNNABLE - start: " +  thread.getName());

        thread.start();

        spareCDL.await();

        thread.interrupt();

        mainCDL.await();

        System.out.println("App complete\n");

        System.out.println("RUNNABLE - finish: " +  thread.getName() + "\n");

        System.out.println("=======MAIN-FINISH: " +  Thread.currentThread().getName());
    }
}
