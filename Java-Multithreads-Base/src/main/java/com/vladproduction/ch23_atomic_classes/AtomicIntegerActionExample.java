package com.vladproduction.ch23_atomic_classes;

import java.util.Arrays;
import java.util.stream.IntStream;

public class AtomicIntegerActionExample {
    public static void main(String[] args) {

        EvenNumberGenerator generator = new EvenNumberGenerator();

        int taskGenerationCounts = 10_000;

        Runnable task = () -> IntStream.range(0, taskGenerationCounts).forEach(i -> generator.generate());

        int amountOfGeneratingThreads = 5;

        Thread[] generatingThreads = createThreads(task, amountOfGeneratingThreads);

        startThreads(generatingThreads);

        waitUntilFinish(generatingThreads);

        int expectedValue = amountOfGeneratingThreads * taskGenerationCounts * 2;
        int actualValue = generator.getValue();

        if(expectedValue != actualValue){
            throw new RuntimeException(
                    "Expected: %d but was: %d".formatted(expectedValue, actualValue)
            );
        }

    }

    private static Thread[] createThreads(Runnable task, int amountOfThreads){
        return IntStream.range(0, amountOfThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    private static void startThreads(Thread[] threads){
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void waitUntilFinish(Thread thread){
        try{
            thread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private static void waitUntilFinish(Thread[] threads){
        Arrays.stream(threads).forEach(AtomicIntegerActionExample::waitUntilFinish);
    }



}
