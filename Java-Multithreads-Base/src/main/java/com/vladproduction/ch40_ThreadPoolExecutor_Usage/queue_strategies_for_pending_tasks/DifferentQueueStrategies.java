package com.vladproduction.ch40_ThreadPoolExecutor_Usage.queue_strategies_for_pending_tasks;

import java.util.concurrent.*;

public class DifferentQueueStrategies {
    public static void main(String[] args) {


        // Unbounded queue - can grow indefinitely
        ExecutorService executor = new ThreadPoolExecutor(
                5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        // Bounded queue - limited capacity
        ExecutorService boundedExecutor = new ThreadPoolExecutor(
                5, 10, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100)
        );

        // Direct handoff - no queueing (uses SynchronousQueue)
        ExecutorService directExecutor = new ThreadPoolExecutor(
                5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );


    }
}
