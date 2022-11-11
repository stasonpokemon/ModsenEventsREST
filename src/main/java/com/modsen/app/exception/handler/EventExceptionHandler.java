package com.modsen.app.exception.handler;

import com.modsen.app.entity.ErrorType;
import com.modsen.app.exception.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EventExceptionHandler {


    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorType> eventExceptionHandler(EventNotFoundException eventNotFoundException) {
        return new ResponseEntity<ErrorType>(ErrorType.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(eventNotFoundException.getMessage())
                .time(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }
}
