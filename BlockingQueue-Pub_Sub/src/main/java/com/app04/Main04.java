package com.app04;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Main04 {
    public static void main(String[] args) {


        BlockingQueue queue = new PriorityBlockingQueue();
        MyPublisher04 publisher = new MyPublisher04(queue);
        publisher.start();


        MySubscriber04 subscriber = new MySubscriber04(queue);
        subscriber.start();

    }
}
