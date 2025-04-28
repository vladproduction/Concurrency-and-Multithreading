package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class ResourceLoader extends ResourceTask{

    private final long secondDuration;

    public ResourceLoader(long id, CountDownLatch latch, long secondDuration) {
        super(id, latch);
        this.secondDuration = secondDuration;
    }

    @Override
    protected void run(CountDownLatch latch) {
        try{
            System.out.printf("%s is loading some resources...\n", this);
            TimeUnit.SECONDS.sleep(secondDuration);
            System.out.printf("%s has finished load some resources.\n", this);
            latch.countDown();
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
