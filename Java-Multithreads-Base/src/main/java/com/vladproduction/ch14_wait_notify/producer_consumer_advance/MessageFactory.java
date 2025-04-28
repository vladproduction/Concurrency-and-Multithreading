package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

public class MessageFactory {

    public static final int NEXT_MESSAGE_INDEX = 1;
    public static final String TEMPLATE_CREATED_MESSAGE_DATA = "Message#%d";

    private int nextMessageIndex;

    public MessageFactory() {
        this.nextMessageIndex = NEXT_MESSAGE_INDEX;
    }

    public Message create() {
        return new Message(String.format(TEMPLATE_CREATED_MESSAGE_DATA, findAndIncrementNextMessageIndex()));
    }

    private synchronized int findAndIncrementNextMessageIndex(){
        return this.nextMessageIndex++;
    }

}
