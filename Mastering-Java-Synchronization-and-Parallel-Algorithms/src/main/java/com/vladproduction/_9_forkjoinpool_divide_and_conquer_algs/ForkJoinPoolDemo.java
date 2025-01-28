package com.vladproduction._9_forkjoinpool_divide_and_conquer_algs;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        long number = 10; // Change this to compute factorial of a different number
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        // Create a factorial task
        FactorialTask task = new FactorialTask(number);
        
        // Invoke the task and get the result
        long result = forkJoinPool.invoke(task);
        
        // Display the result
        System.out.println("Factorial of " + number + " is: " + result);
    }
}
