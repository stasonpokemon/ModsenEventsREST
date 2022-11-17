package com.modsen.app.exception;

public class SortParametersNotValidException extends RuntimeException {
    public SortParametersNotValidException() {
        super();
    }

    public SortParametersNotValidException(String message) {
        super(message);
    }
}
