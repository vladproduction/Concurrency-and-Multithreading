package com.vladproduction._1_intro_concurrency;

public class SimpleThread extends Thread{

    private String threadName;

    SimpleThread(String name) {
        threadName = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + " - Count: " + i);
            try {
                // Sleep for a while to simulate work
                Thread.sleep(500); // Sleep for 500 milliseconds
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted.");
            }
        }
        System.out.println(threadName + " has finished.");
    }

}
