package Exceptions;

public class EmptyQueueException extends RuntimeException {
    
    public EmptyQueueException(String message) {
        super(message);
    }

    public EmptyQueueException() {
        super("EmptyQueueException: the queue is empty and has no front element.");
    }

}
