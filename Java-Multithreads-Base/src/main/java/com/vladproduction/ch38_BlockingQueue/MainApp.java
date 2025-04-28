package com.vladproduction.ch38_BlockingQueue;

import java.util.concurrent.ThreadLocalRandom;

public class MainApp {
    public static void main(String[] args) {

        //create a broker
//        MessageBroker<Integer> broker = new SynchronousMessageBroker<>();
//        MessageBroker<Integer> broker = new LinkedMessageBroker<>(3);
        MessageBroker<Integer> broker = new ArrayMessageBroker<>(3);

        //create threads producing messages:
        startProducing(broker, 1);
        startProducing(broker, 3);
        startProducing(broker, 5);

        //create thread consuming messages:
        startConsuming(broker, 5);
        startConsuming(broker, 3);
        startConsuming(broker, 1);


    }

    //method for consuming elements from broker
    private static void startConsuming(MessageBroker<Integer> broker, int secondTimeout){
        new Thread(new MessageBrokerConsumerTask<>(broker, secondTimeout)).start();
    }

    //method for placing elements into broker
    private static void startProducing(MessageBroker<Integer> broker, int secondTimeout){
        new Thread(new MessageBrokerProducingTask<>(broker, secondTimeout, MainApp::generateInt)).start();
    }

    //method for generating number from 0 to 10
    private static int generateInt() {
        return ThreadLocalRandom.current().nextInt(0, 10);
    }

}
