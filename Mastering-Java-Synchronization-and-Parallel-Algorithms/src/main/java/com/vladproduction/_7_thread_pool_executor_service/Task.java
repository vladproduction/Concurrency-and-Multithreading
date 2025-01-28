package com.vladproduction._7_thread_pool_executor_service;

import java.util.concurrent.Callable;

class Task implements Callable<String> {
    private final String taskName;

    Task(String name) {
        this.taskName = name;
    }

    @Override
    public String call() throws Exception {
        // Simulate work by sleeping
        Thread.sleep(1000);
        return "Result of " + taskName;
    }
}
