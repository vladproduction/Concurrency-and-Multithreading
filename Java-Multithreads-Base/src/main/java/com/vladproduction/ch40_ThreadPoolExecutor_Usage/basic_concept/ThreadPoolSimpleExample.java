package com.vladproduction.ch40_ThreadPoolExecutor_Usage.basic_concept;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * At its core, a thread pool consists of:
 * A pool of worker threads
 * A task queue
 * A management mechanism
 * When you submit a task to a thread pool, it gets placed in the queue. Worker threads continually take tasks from the queue and execute them.
 * */
public class ThreadPoolSimpleExample {
    public static void main(String[] args) {

        //creating thread pool executor with 3 workers threads:
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //submitting the tasks (assuming we have such 10) to the thread pool:
        for (int i = 0; i < 10; i++) {
            final int taskId = i; //holding the task id
            executorService.submit(() -> {
                System.out.printf("Task %d is running by thread %s\n", taskId, Thread.currentThread().getName());
                try{
                    TimeUnit.SECONDS.sleep(1); //simulating processing time
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                System.out.printf("Task %d is finished by thread %s\n", taskId, Thread.currentThread().getName());
                return null;
            });
        }

        //shutdown the executor service when the program is finished:
        executorService.shutdown();

    }
}
