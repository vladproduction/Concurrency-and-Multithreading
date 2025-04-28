package com.vladproduction.ch40_ThreadPoolExecutor_Usage.thread_factory_customization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory {
    public static void main(String[] args) {

        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("CustomWorker-" + counter.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.setDaemon(false);
                return thread;
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(5, factory);


    }
}
