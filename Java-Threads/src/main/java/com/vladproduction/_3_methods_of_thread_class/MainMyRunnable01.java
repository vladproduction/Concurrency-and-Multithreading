package com.vladproduction._3_methods_of_thread_class;

public class MainMyRunnable01 {
    public static void main(String[] args) throws InterruptedException {
        Thread taskThread = new Thread(new MyRunnable01());
        taskThread.start();
//        Thread.sleep(3000);
        taskThread.interrupt();
        Thread.sleep(3000);
        System.out.println(taskThread.isInterrupted());
    }
}
