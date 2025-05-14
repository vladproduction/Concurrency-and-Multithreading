package com.vladproduction.jvm_and_threads;

public class ThreadPriorityExample {
    public static void main(String[] args) {

        Thread highPriorityThread = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                System.out.println("High-Priority Thread: " + i);
            }
        },"High Priority Thread");

        Thread mediumPriorityThread = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                System.out.println("Medium-Priority Thread: " + i);
            }
        },"Medium Priority Thread");

        Thread lowPriorityThread = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                System.out.println("Low-Priority Thread: " + i);
            }
        },"Low Priority Thread");

        highPriorityThread.setPriority(Thread.MAX_PRIORITY);
        mediumPriorityThread.setPriority(Thread.NORM_PRIORITY);
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);

        highPriorityThread.start();
        mediumPriorityThread.start();
        lowPriorityThread.start();


    }
}
