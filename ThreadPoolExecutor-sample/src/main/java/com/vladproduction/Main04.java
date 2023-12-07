package com.vladproduction;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main04 {
    public static void main(String[] args) {


        ThreadPoolExecutor configurableThreadPool = new ThreadPoolExecutor(1, 45,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(300));



        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("START-runnable1; " + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("FINISH-runnable1; " + Thread.currentThread().getName());

            }
        };

        Runnable runnable2 = ()->{
            try {
                System.out.println("START-runnable2; " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("FINISH-runnable2; " + Thread.currentThread().getName());
        };

        Runnable runnable3 = ()->{
            try {
                System.out.println("START-runnable3; " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("FINISH-runnable3; " + Thread.currentThread().getName());
        };


        for (int i = 0; i < 10; i++) {

            System.out.println("before:");
            int activeCountBefore = configurableThreadPool.getActiveCount();
            System.out.println("activeCountBefore = " + activeCountBefore);
            try{
                System.out.println("\tbefore execute#1");
                configurableThreadPool.execute(runnable1);
                System.out.println("\tafter execute#1");


                System.out.println("\tbefore execute#2");
                configurableThreadPool.execute(runnable2);
                System.out.println("\tafter execute#2");


                System.out.println("\tbefore execute#3");
                configurableThreadPool.execute(runnable3);
                System.out.println("\tafter execute#3");

            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("after:");
            int activeCountAfter = configurableThreadPool.getActiveCount();
            System.out.println("activeCountAfter = " + activeCountAfter);

            long taskCount = configurableThreadPool.getTaskCount();
            System.out.println("taskCount = " + taskCount);
        }

        long completedTaskCount = configurableThreadPool.getCompletedTaskCount();
        System.out.println("completedTaskCount = " + completedTaskCount);
        configurableThreadPool.shutdown();

    }
}
