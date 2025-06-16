package com.vladproduction.ch03_methods_of_thread_class;

public class MainMyRunnable01 {
    public static void main(String[] args) throws InterruptedException {

        Thread taskThread = new Thread(new MyRunnable01());

        taskThread.start();

//        Thread.sleep(3000);
        taskThread.interrupt();
        Thread.sleep(3000);
        System.out.println(taskThread.isInterrupted());
    }

    static class MyRunnable01 implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                System.out.println(i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread().isInterrupted());
//                break;
//            }

                if(Thread.interrupted()){
                    break;
                }
            }
        }
    }

}
