package com.vladproduction.threads_demo;

public class Counter {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Counter value: " + counter.getCount());
    }

    private int count = 0;

    //not synchronized
    /*public void increment(){
        count++; //not atomic
    }*/

    //now it is synchronized
    public synchronized void increment(){
        count++; //not atomic
    }

    public int getCount() {
        return count;
    }
}
