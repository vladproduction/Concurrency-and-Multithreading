package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

import java.util.Arrays;

public class BrokerAdvancedApp {
    public static void main(String[] args) {

        //scenarios:
        //  1) heavy-producer: produce more messages than consumer can consume
        //DURATION_TO_SLEEP_BEFORE_PRODUCING = 1;
        //DURATION_TO_SLEEP_BEFORE_CONSUMING = 3;

        //  2) quick-consumer: consume more messages than a producer can produce
        //DURATION_TO_SLEEP_BEFORE_PRODUCING = 3;
        //DURATION_TO_SLEEP_BEFORE_CONSUMING = 1;

        //  3) normal: normal producer and consumer
        //DURATION_TO_SLEEP_BEFORE_PRODUCING = 3;
        //DURATION_TO_SLEEP_BEFORE_CONSUMING = 3;

        int brokerMaxStoredMessages = 15;

        Broker broker = new Broker(brokerMaxStoredMessages);

        MessageFactory messageFactory = new MessageFactory();

        Thread firstProducingThread = new Thread(new ProducerTask(broker, messageFactory, brokerMaxStoredMessages, "PRODUCER_1"));
        Thread secondProducingThread = new Thread(new ProducerTask(broker, messageFactory, 10, "PRODUCER_2"));
        Thread thirdProducingThread = new Thread(new ProducerTask(broker, messageFactory, 5, "PRODUCER_3"));

        Thread firstConsumingThread = new Thread(new ConsumerTask(broker, 0, "CONSUMER_1"));
        Thread secondConsumingThread = new Thread(new ConsumerTask(broker, 6, "CONSUMER_2"));
        Thread thirdConsumingThread = new Thread(new ConsumerTask(broker, 11, "CONSUMER_3"));

        startThreads(firstProducingThread, secondProducingThread, thirdProducingThread,
                     firstConsumingThread, secondConsumingThread, thirdConsumingThread);
    }

    private static void startThreads(Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
