package com.vladproduction.thread_coordination;

import java.util.concurrent.TimeUnit;

public class ProducerConsumerExampleWithLock {
    public static void main(String[] args) {

        SharedBufferWithLock sharedBufferWithLock = new SharedBufferWithLock();

        //producer thread:
        Thread producer = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                sharedBufferWithLock.produce(i);
                try{
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "Producer Thread" );

        //consumer thread:
        Thread consumer = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                sharedBufferWithLock.consume();
                try{
                    TimeUnit.MILLISECONDS.sleep(800);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer Thread");

        producer.start();
        consumer.start();

    }
}
