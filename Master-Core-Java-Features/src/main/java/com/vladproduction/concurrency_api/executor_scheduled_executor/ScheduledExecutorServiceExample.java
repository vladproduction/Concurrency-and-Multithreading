package com.vladproduction.concurrency_api.executor_scheduled_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {
    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(4);
        ExecutorService newSingleScheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

        for (int i = 1; i <= 5; i++) {
            Task task = new Task("Task " + i);
            scheduledExecutorService.schedule(task, i * 2, TimeUnit.SECONDS);
        }
        scheduledExecutorService.scheduleAtFixedRate(
                new Task("Fixed Rate Task"),
                1,
                5,
                TimeUnit.SECONDS);

        scheduledExecutorService.scheduleWithFixedDelay(
                new Task("Fixed Delay Task"),
                2,
                5,
                TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduledExecutorService.shutdown();
        System.out.println("\n\nAll tasks completed. ScheduledExecutorService shutdown gracefully.");
    }
}
