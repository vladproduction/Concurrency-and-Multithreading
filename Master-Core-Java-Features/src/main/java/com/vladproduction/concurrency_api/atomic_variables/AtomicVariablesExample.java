package com.vladproduction.concurrency_api.atomic_variables;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariablesExample {
    public static void main(String[] args) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            executorService.submit(()->{
               String threadName = Thread.currentThread().getName();
               int newValue = atomicInteger.incrementAndGet();
               System.out.println(threadName + " updated value to: " + newValue);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Final value: " + atomicInteger.get());

    }
}
