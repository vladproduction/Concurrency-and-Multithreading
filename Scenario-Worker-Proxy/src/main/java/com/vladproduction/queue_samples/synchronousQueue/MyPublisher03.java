package com.vladproduction.queue_samples.synchronousQueue;

import java.util.concurrent.BlockingQueue;

public class    MyPublisher03 extends Thread{

    private BlockingQueue queue;

    public MyPublisher03(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            System.out.println("publisher created " + i);
            try {
                queue.put(i);
                System.out.println("after put...");
                i++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
