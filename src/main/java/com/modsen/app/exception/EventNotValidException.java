package com.modsen.app.exception;

public class EventNotValidException extends RuntimeException {
    public EventNotValidException() {
        super();
    }

    public EventNotValidException(String message) {
        super(message);
    }
}
