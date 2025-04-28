package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ResourceLoaderFactory extends ResourceTaskFactory{

    private long nextSecondDuration = 1;

    @Override
    protected ResourceLoader create(long id, CountDownLatch latch) {
        return new ResourceLoader(id, latch, nextSecondDuration++);
    }
}
