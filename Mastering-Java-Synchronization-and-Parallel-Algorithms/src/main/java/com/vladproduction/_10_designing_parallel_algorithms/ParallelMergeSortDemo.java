package com.vladproduction._10_designing_parallel_algorithms;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSortDemo {
    public static void main(String[] args) {
        int[] array = {37, 25, 43, 5, 69, 9, 14, 80}; // Unsorted array
        ForkJoinPool pool = new ForkJoinPool(); // Create ForkJoinPool

        // Start the parallel merge sort
        ParallelMergeSort task = new ParallelMergeSort(array, 0, array.length);
        pool.invoke(task); // Invoke the task

        // Display the sorted array
        System.out.println("Sorted array: ");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
