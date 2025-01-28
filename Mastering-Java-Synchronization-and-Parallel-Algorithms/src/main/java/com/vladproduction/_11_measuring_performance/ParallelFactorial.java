package com.vladproduction._11_measuring_performance;

import java.util.concurrent.RecursiveTask;

class ParallelFactorial extends RecursiveTask<Long> {
    private final long number;

    ParallelFactorial(long number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        if (number <= 1) {
            return 1L;
        }
        ParallelFactorial subTask = new ParallelFactorial(number - 1);
        subTask.fork(); // Fork a new task
        return number * subTask.join(); // Join and compute the final result
    }
}
