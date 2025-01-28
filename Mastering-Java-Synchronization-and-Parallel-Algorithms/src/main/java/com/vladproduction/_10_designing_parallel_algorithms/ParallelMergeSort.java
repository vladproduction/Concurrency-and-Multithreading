package com.vladproduction._10_designing_parallel_algorithms;

import java.util.concurrent.RecursiveAction;

class ParallelMergeSort extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;

    ParallelMergeSort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left < 2) {
            return; // Base case: Array is already sorted or has one element
        }

        int mid = (left + right) / 2;

        // Fork the left and right sorting tasks
        ParallelMergeSort leftTask = new ParallelMergeSort(array, left, mid);
        ParallelMergeSort rightTask = new ParallelMergeSort(array, mid, right);

        invokeAll(leftTask, rightTask); // Execute both tasks in parallel

        // Merge the sorted halves
        merge(array, left, mid, right);
    }

    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left];
        int i = left, j = mid, k = 0;

        while (i < mid && j < right) {
            temp[k++] = (array[i] <= array[j]) ? array[i++] : array[j++];
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < right) {
            temp[k++] = array[j++];
        }

        // Copy the sorted subarray back into the original array
        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
