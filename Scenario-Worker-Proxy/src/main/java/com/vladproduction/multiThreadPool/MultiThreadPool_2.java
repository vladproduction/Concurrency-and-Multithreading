package com.vladproduction.multiThreadPool;

import java.util.concurrent.*;

public class MultiThreadPool_2 {

    private static final int QUEUE_CAPACITY = 10;
    private static final BlockingQueue<String> scenarioQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<String> proxyQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final CountDownLatch CDL = new CountDownLatch(8);
    private static final int WORKERS = 6;

    public void starter() throws InterruptedException {

        ExecutorService workersPool = Executors.newFixedThreadPool(WORKERS);
        ExecutorService executorsPool = Executors.newFixedThreadPool(5);

        Runnable scenario = getScenario();
        executorsPool.execute(scenario);
        Runnable proxy = getProxy();
        executorsPool.execute(proxy);

        TimeUnit.SECONDS.sleep(1);

        CompletionService<Void> completionService = getVoidCompletionService(workersPool, WORKERS);
        for (int i = 0; i < WORKERS; i++) {
            completionService.take();
        }

        workersPool.shutdown();
        executorsPool.shutdown();

        executorsPool.awaitTermination(10, TimeUnit.SECONDS);
        workersPool.awaitTermination(10, TimeUnit.SECONDS);


    }

    private static CompletionService<Void> getVoidCompletionService(ExecutorService workersPool, int workers){

        CompletionService<Void> completionService = new ExecutorCompletionService<>(workersPool);

        for (int i = 0; i < workers; i++) {
            completionService.submit(()->{
                try{
                    start();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }finally {
                    CDL.countDown();
                }
                return null;
            });
        }
        return completionService;

    }

    private static void start() throws InterruptedException {

        while (true){
            String scenario = scenarioQueue.poll(1, TimeUnit.SECONDS);
            String proxy = proxyQueue.poll(1, TimeUnit.SECONDS);
            if(scenario == null || proxy == null){
                break;
            }
            System.out.println("worker: " + Thread.currentThread().getName() + "pooled: " + scenario);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("worker: " + Thread.currentThread().getName() + "pooled: " + proxy);
            TimeUnit.SECONDS.sleep(1);
        }

    }

    private static Runnable getScenario() {

        return () -> {
            System.out.println("Scenario queue submit: " + Thread.currentThread().getName());
            try{
                for (int i = 1; i <= 5; i++) {
                    String scenario = " scenario#" + i;
                    scenarioQueue.put(scenario);
                    System.out.println(scenario);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                CDL.countDown();
            }
        };
    }

    private static Runnable getProxy() {

        return () -> {
            System.out.println("Proxy queue submit: " + Thread.currentThread().getName());
            try{
                for (int i = 1; i <= 5; i++) {
                    String scenario = " proxy#" + i;
                    scenarioQueue.put(scenario);
                    System.out.println(scenario);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                CDL.countDown();
            }
        };
    }

}
