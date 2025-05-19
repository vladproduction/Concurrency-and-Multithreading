package com.vladproduction.ch0_training.thread_local_example;

public class Task1 extends Thread {

    private final ThreadLocal<Integer> value;

    public Task1(ThreadLocal<Integer> value) {
        this.value = value;
    }

    @Override
    public void run() {
        while(true){
            int result = value.get();
            value.set(result + 1);
            System.out.println("Task1: " + value.get());
        }
    }
}
