package com.vladproduction.concurrency_design_patterns.worker_thread_pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WorkerThreadPatternExample {
    public static void main(String[] args) {

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        int numberOfWorkers = 3; //pool size

        Worker[] workers = new Worker[numberOfWorkers];

        //start workers
        for (int i = 0; i < numberOfWorkers; i++) {
            workers[i] = new Worker(queue);
            workers[i].start();
        }

        //add tasks to the queue
        for (int i = 1; i <= 10; i++) {
            queue.add(new Task("Task " + i));
        }

        //delay time to work a bit
        try{
            TimeUnit.MILLISECONDS.sleep(12000L);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        //stop worker after all tasks are processed
        for (Worker worker : workers) {
            worker.interrupt();
        }

    }
}
