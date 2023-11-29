package com.vladproduction.lection_example;

import java.util.Queue;
import java.util.concurrent.*;

public class ThreadPoolAndQueue {

    private static final Queue<String> SCENARIO_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<String> PROXY_QUEUE = new ConcurrentLinkedQueue<>();
    private static final CountDownLatch CDL = new CountDownLatch(8);

    public void threadAndQueue() throws InterruptedException {

        //init threadPool
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //description of how threads have to work
        Runnable scenarios = new Runnable() { //responsible to fill scenarios queue
            @Override
            public void run() {
                System.out.println("Scenarios submitted by: " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) { //we will have 10 scenarios
                    String scenario = "\tScenario# " + i;
                    SCENARIO_QUEUE.add(scenario);
                    System.out.println(scenario);
                    try {
                        TimeUnit.SECONDS.sleep(5); //simulating speed of filling queue
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                CDL.countDown();
            }
        };

        Runnable proxyParsing = new Runnable() {
            @Override
            public void run() {
                System.out.println("Proxy parsing: " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    String proxy = "\tProxy# " + i;
                    SCENARIO_QUEUE.add(proxy);
                    System.out.println(proxy);
                    try {
                        TimeUnit.SECONDS.sleep(2); //little faster then scenarios
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
                CDL.countDown();
            }
        };

        //put our threads into threadPool
        executorService.submit(scenarios);
        executorService.submit(proxyParsing);

        //here we create worker, to execute tasks in our queues
        int amountOfWorkers = 6;
        for (int workersCounter = 1; workersCounter <= amountOfWorkers ; workersCounter++) {

            Runnable worker = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        String proxyToUseForWebDriver = PROXY_QUEUE.poll();
                        if(proxyToUseForWebDriver == null) continue;  //if its empty just continue
                        //Initialize webDriver
                        String scenarioToExecute = SCENARIO_QUEUE.poll();
                        System.out.println("worker: " + Thread.currentThread().getName());
                        System.out.println("; using proxy: " + proxyToUseForWebDriver);
                        System.out.println("; and scenario is: " + scenarioToExecute);
                        try {
                            TimeUnit.SECONDS.sleep(4);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    CDL.countDown();
                }
            };
            executorService.submit(worker); //add worker to executor service
        }
        CDL.await(); //waiting till all operations done and only then executor could be shutdown
        executorService.shutdown(); //and execution, stop programme


    }
}
