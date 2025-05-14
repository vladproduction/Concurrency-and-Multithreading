package com.vladproduction.handling_exceptions_in_threads;

import java.util.concurrent.*;

public class CallableExceptionExample {
    public static void main(String[] args) {

        while (true){

            ExecutorService executor = Executors.newSingleThreadExecutor();

            Callable<String> task = () -> {
                System.out.println(Thread.currentThread().getName() + " Task started.");
                double value = Math.random();
                System.out.println("Generated number for thread " + Thread.currentThread().getName() + " is " + value);
                if (value > 0.5) {
                    throw new RuntimeException("Simulated exception!");
                }
                TimeUnit.MILLISECONDS.sleep(1000L);
                return Thread.currentThread().getName() + " Task finished successfully.";
            };

            Future<String> future = executor.submit(task);
            try {
                String result = future.get();
                System.out.println("Task finished with result " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task interrupted.");
            } catch (ExecutionException e) {
                System.out.println("Task failed with exception: " + e.getCause().getMessage());
                break;
            }finally {
                executor.shutdown();
            }

        }

    }
}
