package com.vladproduction.ch41_callable_future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * A Callable task that performs a calculation after a simulated delay.
 * This represents a time-consuming operation that returns a result.
 */
public class CalculationTask implements Callable<Integer> {

    private final int value;
    private final long sleepTime;

    /**
     * Creates a calculation task with the given parameters.
     *
     * @param value the base value for calculation
     * @param sleepTime time in milliseconds to simulate work
     */
    public CalculationTask(int value, long sleepTime) {
        this.value = value;
        this.sleepTime = sleepTime;
    }

    /**
     * The method that will be executed asynchronously.
     *
     * @return the calculated result
     * @throws Exception if an error occurs during execution
     */
    @Override
    public Integer call() throws Exception {
        try{
            //logging for starting process of task:
            System.out.printf("%s -Starting calculation with value: %s, processing time: %d (ms)\n", Thread.currentThread().getName(), value, sleepTime);
            //simulating consuming operation:
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            // Calculate result (just a simple squaring operation for demo)
            int result = value * value;
            //logging result:
            System.out.printf("%s -Completing calculation with result: %s\n", Thread.currentThread().getName(), result);
            return result;

        }catch (InterruptedException interruptedException){
            System.out.println(Thread.currentThread().getName() +
                    " - Calculation interrupted for value: " + value);
            // Propagate the interrupt status
            Thread.currentThread().interrupt();
            throw interruptedException;
        }
    }
}



















