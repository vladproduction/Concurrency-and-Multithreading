package com.vladproduction.ch40_ThreadPoolExecutor_Usage.task_submission_and_execution_flow;

public class TaskSubmission_ExecutionFlows {
    public static void main(String[] args) {

        /*Task Submission and Execution Flow
        When you submit a task to a thread pool, it follows this flow:
        1)Task submission: The client submits a Runnable or Callable task
        2)The thread pool places the task in its internal queue
        3)When a worker thread becomes available, it takes the next task from the queue
        4)The worker thread executes the task
        5)After completion, the worker thread returns to the pool to await another task*/

        // Submitting tasks in different ways
        //ExecutorService executor = Executors.newFixedThreadPool(2);
        //Future<?> future1 = executor.submit(runnable);  // Returns a Future for tracking
        //Future<String> future2 = executor.submit(callable);  // Returns a Future with result
        //executor.execute(runnable);  // No return value

    }

}
