package com.vladproduction.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolCreated {
    public static void main(String[] args) {

        //check how many processes available:
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors: " + processors);

        //create pool
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        for (int i = 0; i < 100; i++) {
            executor.submit(new VegChopper());
        }

        //pool-1-thread-13: Veg Chopper
        //the name is difference; we use pool and reusing threads between number of processors

        //to properly finish program we call shutdown method,
        //and executor not waiting for submitting and executing new tasks
        executor.shutdown();

    }
}

class VegChopper extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Veg Chopper");
    }
}
