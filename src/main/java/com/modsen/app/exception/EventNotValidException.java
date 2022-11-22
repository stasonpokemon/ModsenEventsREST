package com.modsen.app.exception;

public class EventNotValidException extends RuntimeException {
    public EventNotValidException(String message) {
        super(message);
    }
}
