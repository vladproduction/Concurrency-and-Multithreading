package com.vladproduction.lection_example;

import java.util.Queue;
import java.util.concurrent.*;

public class ThreadPoolAndQueue {

    private static final Queue<String> SCENARIO_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<String> PROXY_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int AMOUNT_OF_TASKS = 5;
    private static final CountDownLatch CDL = new CountDownLatch(AMOUNT_OF_TASKS + 2);

    public void threadAndQueue() throws InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(5);


        Runnable scenarios = new Runnable() {
            @Override
            public void run() {
                System.out.println("Scenarios submitted by: " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    String scenario = "Scenario# " + i;
                    SCENARIO_QUEUE.add(scenario);
                    System.out.println(scenario);
                    try {
                        TimeUnit.SECONDS.sleep(1);
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
                    String proxy = "Proxy# " + i;
                    PROXY_QUEUE.add(proxy);
                    System.out.println(proxy);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
                CDL.countDown();
            }
        };

        executorService.submit(scenarios);
        executorService.submit(proxyParsing);

        TimeUnit.SECONDS.sleep(15);

        for (int taskCounter = 1; taskCounter <= AMOUNT_OF_TASKS; taskCounter++) {

            String taskName = "created task# " + taskCounter;
            System.out.println(taskName);

            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println(taskName + "//-------started-------//" + Thread.currentThread().getName());
                    for (int i = 0; i < 10; i++) {
                        String proxyToUseForWebDriver = PROXY_QUEUE.poll();
                        if (proxyToUseForWebDriver == null) continue;
                        String scenarioToExecute = SCENARIO_QUEUE.poll();
                        System.out.println(taskName + "/worker: " + Thread.currentThread().getName() +
                                "; " + taskName + "/using proxy: " + proxyToUseForWebDriver +
                                "; " + taskName + "/and scenario is: " + scenarioToExecute);
                        try {
                            TimeUnit.SECONDS.sleep(10);
                            System.out.println("\t" + taskName + "//worker: " + Thread.currentThread().getName() + " moving to i = " + (i + 1));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    CDL.countDown();
                    System.out.println(taskName + "//-------finish-------//");
                }
            };
            System.out.println("task is submitted to workers");
            executorService.submit(task);
        }
        CDL.await(); //waiting till all operations done and only then executor could be shutdown
        executorService.shutdown(); //and execution, stop programme

    }
}
