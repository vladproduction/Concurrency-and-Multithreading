package com.app03;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Main03 {
    public static void main(String[] args) {

        BlockingQueue queue = new SynchronousQueue();


        MyPublisher03 publisher = new MyPublisher03(queue);
        publisher.start();

//        for (int i = 0; i < 10; i++) {
//            MySubscriber03 subscriber = new MySubscriber03(queue);
//            subscriber.start();
//        }
        MySubscriber03 subscriber = new MySubscriber03(queue);
        subscriber.start();

    }
}
