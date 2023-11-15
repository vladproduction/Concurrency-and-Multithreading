package vladproduction.com.wait_notify.produce_consume_advance.consumer;

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
