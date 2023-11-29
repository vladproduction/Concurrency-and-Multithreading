package com.vladproduction.scenario_worker_proxy;

import java.util.concurrent.*;

public class ProxyQueue extends Thread{

    private static final int CAPACITY = 3;
    private static BlockingQueue<String> proxyQueue;

    public ProxyQueue()  {
        proxyQueue = new LinkedBlockingQueue<>(CAPACITY);

    }

    @Override
    public void run() {
        System.out.println("ProxyQueue-START:" + Thread.currentThread().getName());
        try {

            for (int i = 0; i < CAPACITY; i++) {
                String proxy = "proxy: #" + i;
                proxyQueue.put(proxy);
                System.out.println(proxy);
                Thread.sleep(2000);
            }
            poolProxy();
            System.out.println("ProxyQueue-FINISH:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void poolProxy() throws InterruptedException {
        System.out.println("\tPROXY POOLING NOW");
        while (true) {
            Thread.sleep(2000);
            String proxy = proxyQueue.poll();
            if (proxy == null) {
                break;
            }
            System.out.println("pool: " + proxy);
        }
    }
}
