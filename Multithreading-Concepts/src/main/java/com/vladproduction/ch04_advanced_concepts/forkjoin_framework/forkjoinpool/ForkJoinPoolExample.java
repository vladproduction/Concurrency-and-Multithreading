package com.vladproduction.ch04_advanced_concepts.forkjoin_framework.forkjoinpool;

import com.vladproduction.ch04_advanced_concepts.forkjoin_framework.recursive_task_and_action.SumArrayTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolExample {

    public static void main(String[] args) throws Exception {
        // Create a custom ForkJoinPool with specific parallelism
        int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool customPool = new ForkJoinPool(parallelism);

        // Create a large array to process
        int[] array = new int[100_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        try {
            // Submit a task to the pool
            long sum = customPool.invoke(new SumArrayTask(array, 0, array.length));
            System.out.println("Sum: " + sum);

            // Alternative: Using submit() and future
            // Future<Long> future = customPool.submit(new SumArrayTask(array, 0, array.length));
            // System.out.println("Sum: " + future.get());

            // Execute task with common pool
            long commonPoolSum = ForkJoinPool.commonPool().invoke(new SumArrayTask(array, 0, array.length));
            System.out.println("Common pool sum: " + commonPoolSum);

            // Common pool details
            System.out.println("Common pool parallelism: " + ForkJoinPool.commonPool().getParallelism());
            System.out.println("Common pool size: " + ForkJoinPool.commonPool().getPoolSize());
        } finally {
            // Shutdown the custom pool properly
            customPool.shutdown();
            if (!customPool.awaitTermination(1, TimeUnit.MINUTES)) {
                customPool.shutdownNow();
            }
        }
    }

}
