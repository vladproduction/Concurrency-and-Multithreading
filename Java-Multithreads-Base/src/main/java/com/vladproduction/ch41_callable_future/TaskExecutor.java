package com.vladproduction.ch41_callable_future;

import java.util.concurrent.*;

/**
 * A wrapper class for ExecutorService that provides helper methods
 * to submit various types of Callable tasks.
 */
public class TaskExecutor {

    private final ExecutorService executorService;

    /**
     * Creates a TaskExecutor with the specified number of threads.
     *
     * @param threadCount the number of threads in the pool
     */
    public TaskExecutor(final int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * Submits a calculation task with the given parameters.
     *
     * @param value the base value for calculation
     * @param sleepTime time in milliseconds to simulate work
     * @return a Future representing the pending result
     */
    public Future<Integer> submitCalculationTask(int value, int sleepTime){
        //create instance of CalculationTask
        CalculationTask task = new CalculationTask(value, sleepTime);

        // Submit the task to the executor and return the Future
        return executorService.submit(task);
    }

    /**
     * Submits a task that will throw an exception.
     *
     * @return a Future representing the pending result
     */
    public Future<Integer> submitFailingTask(){
        return executorService.submit(() -> {
            System.out.println("Starting Failing task");
            //simulating some process
            TimeUnit.MILLISECONDS.sleep(500);
            throw new RuntimeException("Task failed deliberately");
        });
    }

    /**
     * Shuts down the executor service.
     */
    public void shutdown(){
        System.out.println("Shutting down executor service");
        executorService.shutdown();
        try{
            // Wait for existing tasks to terminate
            if(!executorService.awaitTermination(5, TimeUnit.SECONDS)){
                // Force shutdown if tasks don't complete in time
                executorService.shutdownNow();
                System.out.println("Executor service shutdown forcefully");
            } else {
                System.out.println("Executor service shutdown gracefully");
            }
        }catch (InterruptedException e){
            // (Re-)Cancel if current thread is interrupted
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("Executor service shutdown interrupted");
        }
    }


}
















