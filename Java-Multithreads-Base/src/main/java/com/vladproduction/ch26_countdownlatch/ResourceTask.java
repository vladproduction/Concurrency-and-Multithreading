package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTask implements Runnable {

    private final long id;
    private final CountDownLatch latch;

    public ResourceTask(long id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public final void run() {
        this.run(this.latch);
    }

    protected abstract void run(CountDownLatch latch);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id = " + this.id + "]";
    }
}
