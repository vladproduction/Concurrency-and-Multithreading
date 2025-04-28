package com.vladproduction.ch26_countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {

        int resourcesCount = 3;
        CountDownLatch latch = new CountDownLatch(resourcesCount);

        ResourceLoaderFactory loaderFactory = new ResourceLoaderFactory();
        Thread[] loadingThreads = createResourceThreads(loaderFactory, resourcesCount, latch);

        ResourceHandlerFactory handlerFactory = new ResourceHandlerFactory();
        int handlingThreadsCount = 4;

        Thread[] handlingThreads = createResourceThreads(handlerFactory, handlingThreadsCount, latch);

        ThreadUtil.startThreads(loadingThreads);
        ThreadUtil.startThreads(handlingThreads);

    }

    private static Thread[] createResourceThreads(ResourceTaskFactory factory,
                                                  int threadsCount,
                                                  CountDownLatch latch) {
        return IntStream.range(0, threadsCount)
                .mapToObj(i -> factory.create(latch))
                .map(Thread::new)
                .toArray(Thread[]::new);
    }



}
