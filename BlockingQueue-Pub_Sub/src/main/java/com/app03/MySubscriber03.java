package com.app03;

import java.util.concurrent.BlockingQueue;

public class MySubscriber03 extends Thread{

    private BlockingQueue queue;

    public MySubscriber03(BlockingQueue queue) {
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
