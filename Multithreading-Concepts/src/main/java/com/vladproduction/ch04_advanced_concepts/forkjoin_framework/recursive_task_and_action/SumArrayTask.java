package com.vladproduction.ch04_advanced_concepts.forkjoin_framework.recursive_task_and_action;

import java.util.concurrent.RecursiveTask;

public class SumArrayTask extends RecursiveTask<Long> {

    private final int[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 1000;

    public SumArrayTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // Sequential computation for small tasks
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split task for parallel execution
            int mid = start + (end - start) / 2;
            SumArrayTask leftTask = new SumArrayTask(array, start, mid);
            SumArrayTask rightTask = new SumArrayTask(array, mid, end);

            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }

}
