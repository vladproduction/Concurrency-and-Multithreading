package com.vladproduction.unit_testing_multithreaded_code;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeCounterTest {


    @Test
    public void testCounterWithMultipleThreads() {

        ThreadSafeCounter counter = new ThreadSafeCounter();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 1000; i++){
            executorService.submit(counter::increment);
        }
        executorService.shutdown();
        try{
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        assertEquals(1000, counter.getCounter(), "Counter should be equal to 1000");

    }

}