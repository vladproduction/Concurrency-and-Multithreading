package com.vladproduction.lock_advanced_synchronization.condition_variable;

public class TestingConditionMain {
    public static void main(String[] args) {

        SharedQueue sharedQueue = new SharedQueue();

        Thread producer = new Thread(()-> {
            for (int i = 1; i <= 10; i++) {
                sharedQueue.produce(i);
            }
        });

        Thread consumer = new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                sharedQueue.consume();
            }
        });

        producer.start();
        consumer.start();


    }
}
