package com.vladproduction.ch17_condition;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class BoundedBufferMainApp {
    public static void main(String[] args) {

        //creating bounded buffer with capacity 5:
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);

        //create a runnable task to produce data for our buffer:
        Runnable producingTask = () -> Stream.iterate(0, i -> i + 1).forEach(i -> {
            try{
                buffer.put(i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        //create thread by using runnable producing task:
        Thread producingThread = new Thread(producingTask);

        //create a runnable task to consume data from our buffer:
        Runnable consumingTask = () -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    buffer.take();
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        //create thread by using runnable consuming task:
        Thread consumingThread = new Thread(consumingTask);

        //start both threads:
        consumingThread.start();
        producingThread.start();

    }
}
