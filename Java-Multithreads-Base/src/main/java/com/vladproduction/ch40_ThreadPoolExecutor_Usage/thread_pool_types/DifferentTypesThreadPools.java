package com.vladproduction.ch40_ThreadPoolExecutor_Usage.thread_pool_types;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DifferentTypesThreadPools {
    public static void main(String[] args) {

        /*Types of Thread Pools
        Different workloads require different thread pool configurations:*/

        //Fixed Thread Pool: Fixed number of threads, good for limiting resource usage
        ExecutorService fixedPool = Executors.newFixedThreadPool(4);

        //Cached Thread Pool: Creates new threads as needed, reuses existing ones when available
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        //Single Thread Executor: Uses a single worker thread
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        //Scheduled Thread Pool: Allows tasks to run after a delay or periodically
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        scheduledPool.schedule(task, 10, TimeUnit.SECONDS); // Run after delay
        scheduledPool.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS); // Run periodically
    }

    private static final Runnable task = () -> System.out.println("Hello World!");
}
