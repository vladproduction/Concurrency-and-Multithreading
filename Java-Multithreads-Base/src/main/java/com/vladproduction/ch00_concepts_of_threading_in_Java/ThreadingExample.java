package com.vladproduction.ch00_concepts_of_threading_in_Java;

/**
 * Approach #1: Threading vs. Process Creation
 * Concept:
 *
 * In a traditional operating system, multitasking is often achieved by creating multiple processes that run independently.
 * Java creates multiple threads within a single process, allowing for concurrent execution within the same application.
 * */
public class ThreadingExample {

    public static void main(String[] args) {
        // Creating and starting two threads
        Thread thread1 = new Thread(new Task(), "Thread-1");
        Thread thread2 = new Thread(new Task(), "Thread-2");

        thread1.start();
        thread2.start();


        //In this code, we create two threads within the same process.
        //Each thread runs the same Task, printing a count from 1 to 5.
        //Both threads share the same process context, making communication between them (if needed) easier.

    }

    static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " - Count: " + i);
                try {
                    Thread.sleep(500); // Simulating work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


