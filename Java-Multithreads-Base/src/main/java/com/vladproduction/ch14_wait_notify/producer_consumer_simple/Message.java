package com.vladproduction.ch14_wait_notify.producer_consumer_simple;

/**
 * Represents a message that is exchanged between producer and consumer
 * through the broker system.
 * */
public class Message {

    private final String data;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Message message = (Message) o;
        return data.equals(message.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Message: %s", this.data);
    }
}
