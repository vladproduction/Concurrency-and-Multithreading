package com.vladproduction.ch42_completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var res = CompletableFuture.supplyAsync(()->{
            try {
                System.out.println("Task-1 started");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Task-1 finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 10;

        }).thenApplyAsync(i-> {
            try {
                System.out.println("Task-2 started");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Task-2 finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return i * 10;
        }).thenAcceptAsync(i-> {

            System.out.println("Task-3 started");

            System.out.println(i);

            System.out.println("Task-3 finished");
        });

        System.out.println("Main finished!");

        res.get();

    }
}
