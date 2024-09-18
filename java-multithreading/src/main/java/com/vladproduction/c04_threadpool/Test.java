package com.vladproduction.c04_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i)); // send task; object of the class implemented Runnable
        }
        executorService.shutdown(); //stop to sending tasks
        System.out.println("all task submitted");

        executorService.awaitTermination(1, TimeUnit.DAYS); //wait to finishing tasks
    }
}

class Work implements Runnable{

    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Work id completed = " + id);
    }
}
