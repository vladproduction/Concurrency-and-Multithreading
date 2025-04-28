package com.vladproduction.ch15_wait_notifyAll.advanced_example;

import java.util.Objects;

/**
 * Represents a message in the producer-consumer system.
 * Immutable class that stores message data.
 */
public class Message {

    private final String data;

    /**
     * Creates a new message with given data
     * @param data Content of the message
     */
    public Message(String data) {
        this.data = data;
    }

    /**
     * @return The data content of this message
     */
    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(data, message.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(data = " + this.data + ")";
    }
}
