package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

/**
 * Factory class for creating sequentially numbered messages.
 * Ensures that each message has a unique sequential identifier.
 */
public class MessageFactory {

    private static final int INITIAL_MESSAGE_INDEX = 1;
    private static final String MESSAGE_DATA_FORMAT = "Message#%d";

    private int nextMessageIndex;

    /**
     * Creates a new message factory with initial index set
     */
    public MessageFactory() {
        this.nextMessageIndex = INITIAL_MESSAGE_INDEX;
    }

    /**
     * Creates a new message with a unique sequential identifier
     * @return Newly created message
     */
    public Message create() {
        return new Message(String.format(MESSAGE_DATA_FORMAT, getAndIncrementNextIndex()));
    }

    /**
     * Thread-safe method to get current index and increment for next use
     * @return Current index before incrementing
     */
    private synchronized int getAndIncrementNextIndex() {
        return this.nextMessageIndex++;
    }

}
