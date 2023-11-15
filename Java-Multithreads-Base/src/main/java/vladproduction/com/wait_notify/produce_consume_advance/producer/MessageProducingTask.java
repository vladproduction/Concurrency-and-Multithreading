package vladproduction.com.wait_notify.produce_consume_advance.producer;

import vladproduction.com.wait_notify.produce_consume_advance.model.Message;
import vladproduction.com.wait_notify.produce_consume_advance.broker.MessageBroker;

import java.util.concurrent.TimeUnit;

public class MessageProducingTask implements Runnable {

    public static final int DURATION_TO_SLEEP_BEFORE_PRODUCING = 1;
    private MessageBroker messageBroker;

    private MessageFactory messageFactory;

    private int maximalAmountMessagesToProduce;
    private String name;

    public MessageProducingTask(MessageBroker messageBroker, MessageFactory messageFactory,
                                int maximalAmountMessagesToProduce, String name) {
        this.messageBroker = messageBroker;
        this.messageFactory = messageFactory;
        this.maximalAmountMessagesToProduce = maximalAmountMessagesToProduce;
        this.name = name;
    }

    public int getMaximalAmountMessagesToProduce() {
        return maximalAmountMessagesToProduce;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message producedMessage = messageFactory.create();
                TimeUnit.SECONDS.sleep(DURATION_TO_SLEEP_BEFORE_PRODUCING);
                messageBroker.produce(producedMessage, this);

            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

}
