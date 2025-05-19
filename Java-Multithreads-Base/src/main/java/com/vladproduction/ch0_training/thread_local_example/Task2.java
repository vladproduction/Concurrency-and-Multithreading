package com.vladproduction.ch0_training.thread_local_example;

public class Task2 extends Thread {

    private final ThreadLocal<Integer> value;

    public Task2(ThreadLocal<Integer> value) {
        this.value = value;
    }

    @Override
    public void run() {
        while(true){
            int result = value.get();
            value.set(result - 1);
            System.out.println("Task2: " + value.get());
        }
    }
}
