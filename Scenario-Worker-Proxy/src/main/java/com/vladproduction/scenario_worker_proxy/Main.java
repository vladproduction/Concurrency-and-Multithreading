package com.vladproduction.scenario_worker_proxy;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ProxyQueue proxyQueue = new ProxyQueue();
        proxyQueue.start();

        ScenarioQueue scenarioQueue = new ScenarioQueue();
        scenarioQueue.start();





    }
}
