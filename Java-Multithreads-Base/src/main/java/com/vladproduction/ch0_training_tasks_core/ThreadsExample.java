package com.vladproduction.ch0_training_tasks_core;

public class ThreadsExample {
    public static void main(String[] args) {
        Runnable r = () -> System.out.print("Cat");

        Thread t = new Thread(r) {
            public void run() {
                System.out.print("Dog");
            }
        };
        t.start();

        //output: Dog
    }
}

/**
 * Output:
 * Since the overridden run() method that prints "Dog" is the effective run() method of the Thread,
 * when the thread starts, it executes this method, resulting in the output being "Dog".
 *
 * Conclusion:
 * The Runnable instance r is not actually executed because the Thread is using its own run() method
 * defined in the anonymous inner class. Hence, the output of the program is "Dog".
 * */