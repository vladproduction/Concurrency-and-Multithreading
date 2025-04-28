package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

/**
 * Main application class that demonstrates the broker system with configurable scenarios.
 * It's easy to switch between different production-consumption patterns.
 */
public class BrokerSimpleApp {

    public static void main(String[] args) {

        // Choose a scenario by changing this line
//        BrokerConfig.Scenario scenario = BrokerConfig.Scenario.BALANCED;
//        BrokerConfig.Scenario scenario = BrokerConfig.Scenario.HEAVY_PRODUCING;
        BrokerConfig.Scenario scenario = BrokerConfig.Scenario.QUICK_CONSUMER;

        // Create broker with capacity from the selected scenario
        Broker broker = new Broker(scenario.getBrokerCapacity());

        // Create producer and consumer tasks with timing from the selected scenario
        Thread producerThread = new Thread(new ProducingTask(
                broker,
                scenario.getProducerDelay(),
                BrokerConfig.MESSAGE_PRODUCED_FORMAT));

        Thread consumerThread = new Thread(new ConsumingTask(
                broker,
                scenario.getConsumerDelay(),
                BrokerConfig.MESSAGE_CONSUMED_FORMAT));

        producerThread.start();
        consumerThread.start();

        // Optional: Display information about the current scenario
        System.out.println("Running scenario: " + scenario);
        System.out.println("Producer delay: " + scenario.getProducerDelay() + " seconds");
        System.out.println("Consumer delay: " + scenario.getConsumerDelay() + " seconds");
        System.out.println("Broker capacity: " + scenario.getBrokerCapacity() + " messages");

    }

}
