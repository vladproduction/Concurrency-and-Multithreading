package com.vladproduction.queue_samples.arrayBlockingQueue;

import java.util.concurrent.BlockingQueue;

public class MySubscriber extends Thread{

    private BlockingQueue queue;

    public MySubscriber(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (true){
            try {
                Thread.sleep(3000);
                Object result = queue.take();
                System.out.println("subscriber consume " + result);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
