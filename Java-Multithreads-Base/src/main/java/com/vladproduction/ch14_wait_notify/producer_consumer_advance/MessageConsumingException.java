package com.vladproduction.ch14_wait_notify.producer_consumer_advance;

public class MessageConsumingException extends RuntimeException{

    public MessageConsumingException() {
    }

    public MessageConsumingException(String description){
        super(description);
    }
    public MessageConsumingException(Exception cause){
        super(cause);
    }
    public MessageConsumingException(String description, Exception cause){
        super(description, cause);
    }
}
