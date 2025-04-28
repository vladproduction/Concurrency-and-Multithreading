package com.vladproduction.ch20_volatile_keyword;

import java.util.concurrent.TimeUnit;

/**
 * Key points about volatile in this example:
 *
 * The volatile keyword ensures that changes to these variables by one thread are immediately visible to the other thread.
 * Without volatile, the producer might update the counter and set dataProduced = true,
 * but the consumer might see an outdated value of either variable due to CPU caching.
 * This is a classic example where thread communication happens through shared variables.
 * */
public class ProducerConsumerVolatileExample {

    private volatile int counter = 0; //The data being produced and consumed

    private volatile boolean dataProduced = false; // A flag indicating whether new data is available

    public static void main(String[] args) {
        ProducerConsumerVolatileExample example = new ProducerConsumerVolatileExample();
        example.startAndJoinThreads();
    }

    private void startAndJoinThreads(){
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();

        try{
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Waits until the consumer has processed the previous data
     * Increments the counter
     * Sets dataProduced to true to signal the consumer
     * */
    class Producer implements Runnable{
        @Override
        public void run() {
            try{
                for (int i = 0; i < 5; i++) {
                    while (dataProduced){
                        Thread.yield();
                    }
                    counter++;
                    System.out.println("Producer: incremented " + counter);

                    dataProduced = true;
                    TimeUnit.MILLISECONDS.sleep(200);

                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * Waits until new data is available
     * Processes the data (reads the counter value)
     * Sets dataProduced to false to signal it's ready for more data
     * */
    class Consumer implements Runnable{
        @Override
        public void run() {
            try{
                for (int i = 0; i < 5; i++) {
                    while (!dataProduced){
                        Thread.yield();
                    }
                    System.out.println("Consumer: processing  " + counter);

                    dataProduced = false; // Signal that we're ready for more data
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }
    }


}
