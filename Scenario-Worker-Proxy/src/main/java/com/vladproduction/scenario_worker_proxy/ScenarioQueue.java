package com.vladproduction.scenario_worker_proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScenarioQueue extends Thread{

    private static final int CAPACITY = 3;
    private static BlockingQueue<String> scenarioQueue;

    public ScenarioQueue()  {
        scenarioQueue = new LinkedBlockingQueue<>(CAPACITY);

    }

    @Override
    public void run() {
        System.out.println("ScenarioQueue-START:" + Thread.currentThread().getName());
        try {

            for (int i = 0; i < CAPACITY; i++) {
                String scenario = "scenario: #" + i;
                scenarioQueue.put(scenario);
                System.out.println(scenario);
                Thread.sleep(2000);
            }
            poolScenario();
            System.out.println("ScenarioQueue-FINISH:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void poolScenario() throws InterruptedException {
        System.out.println("\tSCENARIO POOLING NOW");
        while (true) {
            Thread.sleep(2000);
            String scenario = scenarioQueue.poll();
            if (scenario == null) {
                break;
            }
            System.out.println("pool: " + scenario);
        }
    }
}
