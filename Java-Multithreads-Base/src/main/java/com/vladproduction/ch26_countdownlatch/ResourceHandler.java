package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;

public final class ResourceHandler extends ResourceTask{

    public ResourceHandler(long id, CountDownLatch latch) {
        super(id, latch);
    }

    @Override
    protected void run(CountDownLatch latch) {
        try{
            latch.await();
            System.out.printf("Resources were handled by %s\n", this);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
