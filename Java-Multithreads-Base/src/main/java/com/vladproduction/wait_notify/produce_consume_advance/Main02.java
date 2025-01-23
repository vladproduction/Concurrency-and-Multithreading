package com.vladproduction.wait_notify.produce_consume_advance;

import com.vladproduction.wait_notify.produce_consume_advance.broker.MessageBroker;
import com.vladproduction.wait_notify.produce_consume_advance.consumer.MessageConsumingTask;
import com.vladproduction.wait_notify.produce_consume_advance.producer.MessageFactory;
import com.vladproduction.wait_notify.produce_consume_advance.producer.MessageProducingTask;

import java.util.Arrays;

public class Main02 {
    public static void main(String[] args) {

        int brokerMaxStoredMessages = 15;

        MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages);

        MessageFactory messageFactory = new MessageFactory();

        Thread firstProducingThread = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, brokerMaxStoredMessages, "Producer_1"));
        Thread secondProducingThread = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, 10, "Producer_2"));
        Thread thirdProducingThread = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, 5, "Producer_3"));

        Thread firstConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 0, "Consumer_1"));
        Thread secondConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 6, "Consumer_2"));
        Thread thirdConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 11, "Consumer_3"));

        startThreads(firstProducingThread, secondProducingThread, thirdProducingThread,
                     firstConsumingThread, secondConsumingThread, thirdConsumingThread);
    }

    private static void startThreads(Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
