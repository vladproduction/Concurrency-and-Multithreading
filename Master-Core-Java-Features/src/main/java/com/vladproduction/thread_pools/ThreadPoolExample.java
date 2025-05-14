package com.vladproduction.thread_pools;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<Integer> task1 = () -> {
            System.out.println(Thread.currentThread().getName() + " is calculating of square of 5.");
            return 5 * 5;
        };

        Callable<Integer> task2 = () -> {
            System.out.println(Thread.currentThread().getName() + " is calculating of square of 7.");
            return 7 * 7;
        };

        Callable<Integer> task3 = () -> {
            System.out.println(Thread.currentThread().getName() + " is calculating of square of 10.");
            return 10 * 10;
        };

        Future<Integer> future1 = executorService.submit(task1);
        Future<Integer> future2 = executorService.submit(task2);
        Future<Integer> future3 = executorService.submit(task3);
        try{
            System.out.println("Result 1 (square of 5): " + future1.get());
            System.out.println("Result 2 (square of 7): " + future2.get());
            System.out.println("Result 3 (square of 10): " + future3.get());
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }finally {
            executorService.shutdownNow();
        }

    }
}
