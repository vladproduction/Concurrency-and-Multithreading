package vladproduction.receiver;

import vladproduction.broker.MessageBroker;
import vladproduction.model.Message;

import static java.lang.System.out;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MessageReceiverTask implements Runnable{
    private static final int DURATION_BEFORE_RECEIVE = 1;
    private static final String DELIVERY_REPORT_OF_RECEIVE = " '%s' consumed\n";

    private final MessageBroker messageBroker;

    public MessageReceiverTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        try{
            while (!currentThread().isInterrupted()){
                SECONDS.sleep(DURATION_BEFORE_RECEIVE);
                Message receiveMessage = messageBroker.receiveMessage();
                out.printf(String.format(DELIVERY_REPORT_OF_RECEIVE, receiveMessage));
            }
        }catch (InterruptedException interruptedException){
            currentThread().interrupt();
        }
    }
}
