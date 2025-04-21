package com.vladproduction.ch04_advanced_concepts.forkjoin_framework.recursive_task_and_action;

import java.util.concurrent.RecursiveAction;

public class ArrayTransformAction extends RecursiveAction {

    private final int[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 1000;

    public ArrayTransformAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            // Sequential transformation for small segments
            for (int i = start; i < end; i++) {
                array[i] = array[i] * 2; // Double each element
            }
        } else {
            // Split task for parallel execution
            int mid = start + (end - start) / 2;
            ArrayTransformAction left = new ArrayTransformAction(array, start, mid);
            ArrayTransformAction right = new ArrayTransformAction(array, mid, end);

            invokeAll(left, right); // Fork both tasks and wait for completion
        }
    }

}
