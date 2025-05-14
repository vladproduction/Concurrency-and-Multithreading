package com.vladproduction.concurrency_design_patterns.worker_thread_pattern;

public class Task implements Runnable {

    private final String taskId;

    public Task(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " processing task: " + taskId);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " completed task: " + taskId);
    }
}
