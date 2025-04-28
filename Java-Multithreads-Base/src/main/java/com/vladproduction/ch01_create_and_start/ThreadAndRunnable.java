package com.vladproduction.ch01_create_and_start;

public class ThreadAndRunnable {
    public static void main(String[] args) {
        ThreadAndRunnable threadAndRunnable = new ThreadAndRunnable();
        threadAndRunnable.runAction();
    }

    private void runAction() {
        Thread thread1 = new ObjectThread();
        thread1.start();
        Thread thread2 = new Thread(new ObjectAtThread());
        thread2.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.interrupt();
        thread2.interrupt();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ThreadAndRunnable thread will continue");
    }
}

class ObjectAtThread implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread-2: start");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Thread-2 has been interrupted");
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread-2: amount = " + i);
        }
    }
}

class ObjectThread extends Thread{
    @Override
    public void run() {
        System.out.println("Thread-1: start");
        for (int i = 0; i < 5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread been interrupted");
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread-1: amount = " + i);
        }
    }
}

