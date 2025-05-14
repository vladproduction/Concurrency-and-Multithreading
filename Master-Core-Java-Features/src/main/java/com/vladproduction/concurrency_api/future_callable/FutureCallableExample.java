package com.vladproduction.concurrency_api.future_callable;

import java.util.concurrent.*;

public class FutureCallableExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> callableTask = () -> {
          String threadName = Thread.currentThread().getName();
          System.out.println(threadName + " is running task.");
          try{
              TimeUnit.SECONDS.sleep(2);
          }catch (InterruptedException e){
              Thread.currentThread().interrupt();
          }

          return threadName + " completed task.";
        };

        Future<String> futureResult = executorService.submit(callableTask);
        try{
            System.out.println("Result: " + futureResult.get());
        }catch (InterruptedException | ExecutionException e){
            Thread.currentThread().interrupt();
        }
        executorService.shutdown();

    }
}
