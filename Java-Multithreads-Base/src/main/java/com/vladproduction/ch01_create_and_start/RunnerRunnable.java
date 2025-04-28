package com.vladproduction.ch01_create_and_start;

import static java.lang.Thread.currentThread;

public class RunnerRunnable {
    public static void main(String[] args) {
        Runnable task = () -> System.out.println(currentThread().getName());
        Thread thread = new Thread(task);
        thread.start();
    }

}
