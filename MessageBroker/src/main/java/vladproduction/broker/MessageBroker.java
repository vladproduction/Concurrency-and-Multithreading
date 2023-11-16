package vladproduction.broker;

import vladproduction.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.Thread.*;

public class MessageBroker {

    private final Queue<Message> messagesQueue;
    private final int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.messagesQueue = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void createMessage(Message message){
        try{
            while (messagesQueue.size() >= maxStoredMessages){
                wait();
            }
            messagesQueue.add(message);
            notify();
        }catch (InterruptedException interruptedException){
            currentThread().interrupt();
        }
    }

    public synchronized Message receiveMessage() {
        try {
            while (messagesQueue.isEmpty()){
                wait();
            }
            Message receivedMessage = messagesQueue.poll();
            notify();
            return receivedMessage;
        }catch (InterruptedException interruptedException){
            currentThread().interrupt();
            throw new RuntimeException(interruptedException);
        }
    }
}
