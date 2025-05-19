package com.vladproduction.ch0_training.thread_local_example;

public class MainTasks {
    public static void main(String[] args) {

        ThreadLocal<Integer> value = ThreadLocal.withInitial(() -> 0);

        Task1 task1 = new Task1(value);
        Task2 task2 = new Task2(value);

        task1.start();
        task2.start();

    }
}
