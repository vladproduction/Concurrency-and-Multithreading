package com.vladproduction.queue_samples.priorityBlockingQueue;

import java.util.concurrent.BlockingQueue;

public class MyPublisher04 extends Thread{

    private BlockingQueue queue;

    public MyPublisher04(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= 5){
            System.out.println("publisher created: " + i);
            try {
                Person04 person04 = new Person04();
                person04.setName("name" + i);
                person04.setAge(i);
                queue.put(person04);
                System.out.println("after put...person to queue: " + i);
                i++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
