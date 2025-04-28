package com.vladproduction.ch11_threads_factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class DemoExample {
    public static void main(String[] args) {

        ThreadFactory threadFactory = new MyThreadFactory();

        Thread thread1 = threadFactory.newThread(new Task());
        Thread thread2 = threadFactory.newThread(new Task());

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public static final class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Hello World");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public static final class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable);
        }
    }

}
