package com.vladproduction.concurrency_api.executor_scheduled_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 5; i++) {
            Task task = new Task("Task " + i);
            executorService.submit(task);
        }
        executorService.shutdown();
        System.out.println("\nAll tasks submitted.");

        try{
            if(!executorService.awaitTermination(60, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        }catch (InterruptedException e){
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("\n\nAll tasks completed. Executor service shutdown gracefully.");

    }

}
