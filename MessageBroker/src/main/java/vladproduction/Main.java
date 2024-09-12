package vladproduction;

import vladproduction.broker.MessageBroker;
import vladproduction.producer.MessageProducingTask;
import vladproduction.receiver.MessageReceiverTask;

public class Main {
    public static void main(String[] args) {

        final int brokerMaxStoredMessage = 5;
        MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessage);

        Thread creatingThread = new Thread(new MessageProducingTask(messageBroker));
        Thread receiveThread = new Thread(new MessageReceiverTask(messageBroker));

        creatingThread.start();
        receiveThread.start();



    }
}
