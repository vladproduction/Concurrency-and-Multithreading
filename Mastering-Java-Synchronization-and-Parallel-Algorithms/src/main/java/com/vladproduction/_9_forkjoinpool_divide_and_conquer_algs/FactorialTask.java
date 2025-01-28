package com.vladproduction._9_forkjoinpool_divide_and_conquer_algs;

import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<Long> {
    private final long number;

    FactorialTask(long number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        if (number <= 1) {
            return 1L; // Base case
        }

        // Split the task into two subtasks
        FactorialTask subTask1 = new FactorialTask(number - 1);
        subTask1.fork(); // Fork the subtask

        // Compute the result of the current task
        long result = number * subTask1.join(); // Join the result of subtasks

        return result;
    }
}
