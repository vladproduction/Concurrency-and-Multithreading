package vladproduction.producer;

import vladproduction.broker.MessageBroker;
import vladproduction.model.Message;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.lang.System.*;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.*;

public class MessageProducingTask implements Runnable{

    private static final String MESSAGE_PRODUCED = " '%s' is created\n";
    private static final int DURATION_BEFORE_CREATING_MESSAGE = 1;

    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;

    public MessageProducingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        this.messageFactory = new MessageFactory();
    }

    @Override
    public void run() {
        try{
            while (!currentThread().isInterrupted()){
                Message producedMessage = messageFactory.create();
                SECONDS.sleep(DURATION_BEFORE_CREATING_MESSAGE);
                messageBroker.createMessage(producedMessage);
                out.printf(String.format(MESSAGE_PRODUCED, producedMessage));
            }
        }catch (InterruptedException interruptedException){
            currentThread().interrupt();
        }
    }

    private static class MessageFactory{
        private static final int NEXT_MESSAGE_INDEX = 1;
        private static final String TEMPLATE_MESSAGE_DATA = "#%d";
        private int nextMessageIndex;

        public MessageFactory() {
            this.nextMessageIndex = NEXT_MESSAGE_INDEX;
        }

        public Message create(){
            return new Message(format(TEMPLATE_MESSAGE_DATA, nextMessageIndex++));
        }
    }

}
