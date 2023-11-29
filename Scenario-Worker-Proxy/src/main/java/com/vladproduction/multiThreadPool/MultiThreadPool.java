package com.vladproduction.multiThreadPool;

import java.util.concurrent.*;

public class MultiThreadPool {

    private static final int QUEUE_CAPACITY = 10;
    private static final BlockingQueue<String> scenarioQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<String> proxyQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final CountDownLatch CDL = new CountDownLatch(8);

    public void starter(int workers) throws Exception {

        ExecutorService workersPool = Executors.newFixedThreadPool(workers);

        ExecutorService executorsPool = Executors.newFixedThreadPool(5);

        Runnable scenarioRunnable = getScenario();
        executorsPool.execute(scenarioRunnable);

        Runnable proxyRunnable = getProxy();
        executorsPool.execute(proxyRunnable);

        CompletionService<Void> completionService = getVoidCompletionService(workersPool, workers);
        for (int i = 0; i < workers; i++) {
            completionService.take();
        }

        executorsPool.shutdown();
        workersPool.shutdown();

        executorsPool.awaitTermination(5, TimeUnit.SECONDS);
        workersPool.awaitTermination(5, TimeUnit.SECONDS);

    }

    private static CompletionService<Void> getVoidCompletionService(ExecutorService workersPool, int workers) {

        CompletionService<Void> completionService = new ExecutorCompletionService<>(workersPool);
        for (int i = 0; i < workers; i++) {
            completionService.submit(() -> {
                try {
                    action();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    CDL.countDown();
                }
                return null;
            });
        }
        return completionService;
    }


    private static void action() throws InterruptedException {
        while (true) {
            String scenario = scenarioQueue.poll(1, TimeUnit.SECONDS);
            String proxy = proxyQueue.poll(1, TimeUnit.SECONDS);
            if (scenario == null || proxy == null) {
                break;
            }
            System.out.printf("worker: %s %s %s%n", Thread.currentThread().getName(), scenario, proxy);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    private static Runnable getProxy() {
        return () -> {
            try {
                System.out.printf("%s Proxy submit%n",
                        Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    String proxy = "\tProxy " + i;
                    proxyQueue.put(proxy);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                CDL.countDown();
            }
        };
    }

    private static Runnable getScenario() {
        return () -> {
            try {
                System.out.printf("%s Scenario submit%n",
                        Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    String scenario = "\tScenario " + i;
                    scenarioQueue.put(scenario);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                CDL.countDown();
            }
        };
    }


}
