package com.vladproduction._9_data_race;

import java.util.concurrent.atomic.AtomicInteger;

public class ShopperAtomic extends Thread{

    static AtomicInteger atomicIntGarlicCounter = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 100_000; i++) {
            atomicIntGarlicCounter.incrementAndGet();
        }
    }
}
