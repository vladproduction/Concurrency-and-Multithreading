package com.vladproduction.thread_coordination;

import java.util.concurrent.TimeUnit;

public class ProducerConsumerExample {
    public static void main(String[] args) {

        SharedBuffer sharedBuffer = new SharedBuffer();

        Thread producer = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                sharedBuffer.produce(i);
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "Producer Thread" );

        Thread consumer = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                sharedBuffer.consume();
                try{
                    TimeUnit.MILLISECONDS.sleep(800);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer Thread");

        producer.start();
        consumer.start();

        try{
            producer.join();
            consumer.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }
}
