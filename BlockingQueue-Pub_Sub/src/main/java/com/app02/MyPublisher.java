package com.app02;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class MyPublisher extends Thread{

    private BlockingQueue queue;

    public MyPublisher(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            System.out.println("publisher created " + i);
            try {
                queue.put(i);
                i++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
