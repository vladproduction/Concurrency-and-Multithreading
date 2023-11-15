package vladproduction.com.wait_notify.produce_consume_simple;

import vladproduction.com.wait_notify.produce_consume_simple.broker.MessageBroker;
import vladproduction.com.wait_notify.produce_consume_simple.consumer.MessageConsumingTask;
import vladproduction.com.wait_notify.produce_consume_simple.producer.MessageProducingTask;

public class Main01 {
    public static void main(String[] args) {
        int brokerMaxStoredMessages = 5;
        MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages);

        Thread producingThread = new Thread(new MessageProducingTask(messageBroker));
        Thread consumingThread = new Thread(new MessageConsumingTask(messageBroker));
        producingThread.start();
        consumingThread.start();
    }
}
