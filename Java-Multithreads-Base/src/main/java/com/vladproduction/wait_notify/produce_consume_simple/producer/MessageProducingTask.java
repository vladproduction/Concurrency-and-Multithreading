package com.vladproduction.wait_notify.produce_consume_simple.producer;

import com.vladproduction.wait_notify.produce_consume_simple.broker.MessageBroker;
import com.vladproduction.wait_notify.produce_consume_simple.model.Message;

import java.util.concurrent.TimeUnit;

public class MessageProducingTask implements Runnable {
    public static final String MESSAGE_IS_PRODUCED = "Message '%s' is produced\n";
    public static final int DURATION_TO_SLEEP_BEFORE_PRODUCING = 3;
    private MessageBroker messageBroker;

    private FactoryMessage factoryMessage;

    public MessageProducingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        this.factoryMessage = new FactoryMessage();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message producedMessage = factoryMessage.create();
                TimeUnit.SECONDS.sleep(DURATION_TO_SLEEP_BEFORE_PRODUCING);
                messageBroker.produce(producedMessage);
                System.out.printf(MESSAGE_IS_PRODUCED, producedMessage);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    public static final class FactoryMessage {

        public static final int NEXT_MESSAGE_INDEX = 1;
        public static final String TEMPLATE_CREATED_MESSAGE_DATA = "Message#%d";

        private int nextMessageIndex;

        public FactoryMessage() {
            this.nextMessageIndex = NEXT_MESSAGE_INDEX;
        }

        public Message create() {
            return new Message(String.format(TEMPLATE_CREATED_MESSAGE_DATA, this.nextMessageIndex++));
        }

    }

}
