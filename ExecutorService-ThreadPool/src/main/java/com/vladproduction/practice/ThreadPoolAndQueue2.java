package com.vladproduction.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolAndQueue2 {

    private static final int QUEUE_CAPACITY = 5;
    private static final BlockingQueue<String> scenarioQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<String> proxyQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final CountDownLatch CDL = new CountDownLatch(8);
    private static final int WORKERS = 4;
    private static final String EXIT = "EXIT";


    public void starter() throws InterruptedException, ExecutionException {

        ExecutorService workersPool = Executors.newFixedThreadPool(WORKERS);
        ExecutorService executorsPool = Executors.newFixedThreadPool(3);

        Callable scenario = getScenario(10);
        Future futureScenario = executorsPool.submit(scenario);

        Callable proxy = getProxy(10);
        Future futureProxy = executorsPool.submit(proxy);

        //futureScenario.get();
        System.out.println("\t\tScenario are added for execution");
        //futureProxy.get();
        System.out.println("\t\tProxy are added for parsing");


        List<Future> futures = getFutures(workersPool, WORKERS);
        for (int i = 0; i < futures.size(); i++) {
            Future concreteFuture = futures.get(i);
            concreteFuture.get();
        }

        workersPool.shutdown();
        executorsPool.shutdown();

    }

    private static List<Future> getFutures(ExecutorService workersPool, int workers) {


        List<Future> futureList = new ArrayList<>();

        for (int i = 0; i < workers; i++) {
            Future future = workersPool.submit(() -> {

                try {
                    while (true) {
                        //System.out.println("before taking from scenarioQueue");
                        String scenario = scenarioQueue.take();
                        //System.out.println("before taking from proxyQueue");
                        String proxy = proxyQueue.take();

                        System.out.println("worker: " + Thread.currentThread().getName() + "//pooled scenario: " + scenario);
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("worker: " + Thread.currentThread().getName() + "//pooled proxy: " + proxy);
                        TimeUnit.SECONDS.sleep(1);
                        if (EXIT.equals(scenario) || EXIT.equals(proxy)) {
                            break;
                        }
                    }
                    scenarioQueue.put(EXIT);
                    proxyQueue.put(EXIT);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    CDL.countDown();
                }
                return null;
            });
            futureList.add(future);
        }
        return futureList;

    }

    private static Callable getScenario(int count) {

        return () -> {
            System.out.println("Scenario queue submit: " + Thread.currentThread().getName());
            try {
                for (int i = 1; i <= count; i++) {
                    String scenario = " scenario#" + i;
                    scenarioQueue.put(scenario);
                    System.out.println("added: " + scenario);
                }
                scenarioQueue.put(EXIT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                CDL.countDown();
            }
            return null;
        };
    }

    private static Callable getProxy(int count) {

        return () -> {
            System.out.println("Proxy queue submit: " + Thread.currentThread().getName());
            try {
                for (int i = 1; i <= count; i++) {
                    String proxy = " proxy#" + i;
                    proxyQueue.put(proxy);
                    System.out.println("added: " + proxy);
                }
                proxyQueue.put(EXIT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                CDL.countDown();
            }
            return null;
        };
    }

}
