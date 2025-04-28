package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;

public final class ResourceHandlerFactory extends ResourceTaskFactory{


    @Override
    protected ResourceHandler create(long id, CountDownLatch latch) {
        return new ResourceHandler(id, latch);
    }
}
