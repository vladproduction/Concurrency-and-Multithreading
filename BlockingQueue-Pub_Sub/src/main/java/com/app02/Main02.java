package com.app02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Flow;

public class Main02 {
    public static void main(String[] args) {

        BlockingQueue queue = new ArrayBlockingQueue(10);
        MyPublisher publisher = new MyPublisher(queue);
        publisher.start();

        for (int i = 0; i < 10; i++) {
            MySubscriber subscriber = new MySubscriber(queue);
            subscriber.start();
        }

    }
}
