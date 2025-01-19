package com.vladproduction._6_creating_multiple_threads;

public class TestThread implements Runnable{

    private String threadName;
    Thread thread;

    public TestThread(String threadName) {
        this.threadName = threadName;
        thread = new Thread(this, threadName);
        System.out.println("New thread created: " + threadName);
        thread.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 4; i > 0; i--){
                System.out.println(threadName + " : " + i);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " is interrupted.");
        } finally {
            System.out.println(threadName + " is exiting.");
        }

    }


}
