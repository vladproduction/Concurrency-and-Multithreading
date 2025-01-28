package com.vladproduction._11_measuring_performance;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PerformanceMeasurementDemo {
    public static void main(String[] args) {
        long number = 20; // Factorial of 20
        
        // Measure Sequential Factorial Performance
        long startTime = System.currentTimeMillis();
        long sequentialResult = SequentialFactorial.computeFactorial(number);
        long sequentialTime = System.currentTimeMillis() - startTime;

        // Print Sequential Result and Time
        System.out.println("Sequential Factorial: " + sequentialResult);
        System.out.println("Time taken (sequential): " + sequentialTime + " ms");

        // Measure Parallel Factorial Performance
        ForkJoinPool pool = new ForkJoinPool();
        startTime = System.currentTimeMillis();
        long parallelResult = pool.invoke(new ParallelFactorial(number));
        long parallelTime = System.currentTimeMillis() - startTime;

        // Print Parallel Result and Time
        System.out.println("Parallel Factorial: " + parallelResult);
        System.out.println("Time taken (parallel): " + parallelTime + " ms");
    }
}
