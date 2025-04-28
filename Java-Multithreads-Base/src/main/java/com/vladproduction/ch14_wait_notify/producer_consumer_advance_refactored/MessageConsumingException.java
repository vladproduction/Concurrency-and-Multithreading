package com.vladproduction.ch14_wait_notify.producer_consumer_advance_refactored;

/**
 * Exception thrown when message consumption fails.
 */
public class MessageConsumingException extends RuntimeException{

    /**
     * Creates a new exception with no message
     */
    public MessageConsumingException() {
    }

    /**
     * Creates a new exception with the specified detail message
     * @param description The detail message
     */
    public MessageConsumingException(String description) {
        super(description);
    }

    /**
     * Creates a new exception with the specified cause
     * @param cause The cause of this exception
     */
    public MessageConsumingException(Exception cause) {
        super(cause);
    }

    /**
     * Creates a new exception with the specified detail message and cause
     * @param description The detail message
     * @param cause The cause of this exception
     */
    public MessageConsumingException(String description, Exception cause) {
        super(description, cause);
    }

}
