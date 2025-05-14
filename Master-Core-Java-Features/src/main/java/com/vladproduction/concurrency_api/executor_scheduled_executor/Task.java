package com.vladproduction.concurrency_api.executor_scheduled_executor;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {

        System.out.printf("\nExecuting task: %s by thread: %s", taskName, Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("\n%s task completed", taskName);

    }
}
