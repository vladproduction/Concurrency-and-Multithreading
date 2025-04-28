package com.vladproduction.ch0_training_tasks_core;

/**
 * Does the main() method start a new thread?
 * Yes.
 *  + No.
 * */
public class ThreadExample02 {
    public static void main(String[] args) {
        // This runs on the main thread
        System.out.println("Main thread running");

        // Create a new thread
        Thread thread = new Thread(() -> System.out.println("New thread running"));

        // Start the new thread
        thread.start(); // This starts a new thread
    }
}

/**
 * The main() method in Java does not start a new thread by itself.
 * Instead, it runs on the main thread of the Java Virtual Machine (JVM) when the program is executed.
 *
 * If you want to start a new thread, you would need to create an instance of the Thread class or
 * implement the Runnable interface and then call the start() method on that thread instance.
 * */
