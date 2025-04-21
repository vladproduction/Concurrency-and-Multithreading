package com.vladproduction.ch04_advanced_concepts.forkjoin_framework.work_stealing_algo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {

    private final int n;
    private static final int THRESHOLD = 10;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= THRESHOLD) {
            // For small problems, compute directly
            return computeSequentially();
        }

        // Split the task into smaller subtasks
        FibonacciTask f1 = new FibonacciTask(n - 1);
        FibonacciTask f2 = new FibonacciTask(n - 2);

        // Fork f1 task to potentially be stolen by another thread
        f1.fork();

        // Compute f2 directly on this thread
        Long f2Result = f2.compute();

        // Join with the result of f1
        Long f1Result = f1.join();

        // Combine results
        return f1Result + f2Result;
    }

    private Long computeSequentially() {
        if (n <= 1) return (long) n;
        long fib1 = 0, fib2 = 1, fibonacci = 0;
        for (int i = 2; i <= n; i++) {
            fibonacci = fib1 + fib2;
            fib1 = fib2;
            fib2 = fibonacci;
        }
        return fibonacci;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask task = new FibonacciTask(40);
        long result = pool.invoke(task);
        System.out.println("Fibonacci(40) = " + result);
        System.out.println("Parallelism level: " + pool.getParallelism());
        System.out.println("Pool size: " + pool.getPoolSize());
        System.out.println("Steal count: " + pool.getStealCount());
    }

}
