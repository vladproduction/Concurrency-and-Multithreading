package com.vladproduction.ch27_cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public final class LeafTask extends Task{

    private final long secondsDuration;
    private final CyclicBarrier cyclicBarrier;

    public LeafTask(long id, long secondsDuration, CyclicBarrier cyclicBarrier) {
        super(id);
        this.secondsDuration = secondsDuration;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void perform() {
        try{
            System.out.printf("%s is starting...\n", this);
            TimeUnit.SECONDS.sleep(secondsDuration);
            System.out.printf("%s is finished\n", this);
            cyclicBarrier.await();
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
        catch (BrokenBarrierException brokenBarrierException){
            throw new RuntimeException(brokenBarrierException);
        }
    }
}
