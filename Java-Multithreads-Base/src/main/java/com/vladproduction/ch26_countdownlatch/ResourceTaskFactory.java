package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTaskFactory {

    private long nextId;

    public ResourceTask create(CountDownLatch countDownLatch) {
        return create(nextId++, countDownLatch);
    }

    protected abstract ResourceTask create(long id, CountDownLatch latch);

}
