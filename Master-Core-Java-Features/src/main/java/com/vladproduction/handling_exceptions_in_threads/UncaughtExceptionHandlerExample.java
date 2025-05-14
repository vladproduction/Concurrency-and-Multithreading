package com.vladproduction.handling_exceptions_in_threads;

import java.util.concurrent.TimeUnit;

public class UncaughtExceptionHandlerExample {
    public static void main(String[] args) {

        Thread.UncaughtExceptionHandler handler = (t, e) -> {
            System.out.println("Thread: " + t.getName() + "terminated with exception: " + e.getMessage());
        };

        Runnable task = () -> {
            while (true) {
                try{
                    System.out.println(Thread.currentThread().getName() + " Task started.");
                    double value = Math.random();
                    System.out.println("Generated number for thread " + Thread.currentThread().getName() + " is " + value);
                    if(value > 0.5){
                        throw new RuntimeException("Simulated exception!");
                    }
                    TimeUnit.MILLISECONDS.sleep(1000L);
                    System.out.println(Thread.currentThread().getName() + " Task finished successfully.");
                }catch (Exception e){
                    System.out.println(Thread.currentThread().getName() + " Caught exception: " + e.getMessage());
                    break;
                }
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        thread1.setUncaughtExceptionHandler(handler);

        Thread thread2 = new Thread(task, "Thread-2");
        thread2.setUncaughtExceptionHandler(handler);

        thread1.start();
        thread2.start();

    }
}
