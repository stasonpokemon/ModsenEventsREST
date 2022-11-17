package com.modsen.app.exception.handler;

import com.modsen.app.exception.SortParametersNotValidException;
import com.modsen.app.exception.dto.ErrorTypeResponseDTO;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.exception.EventNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EventExceptionHandler {


    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorTypeResponseDTO> eventNotFoundExceptionHandler(EventNotFoundException eventNotFoundException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(eventNotFoundException.getMessage())
                .time(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotValidException.class)
    public ResponseEntity<ErrorTypeResponseDTO> eventNotValidExceptionHandler(EventNotValidException eventNotValidException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(eventNotValidException.getMessage())
                .time(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorTypeResponseDTO> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(httpMessageNotReadableException.getMessage())
                .time(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SortParametersNotValidException.class)
    public ResponseEntity<ErrorTypeResponseDTO> eventSortParamNotValidExceptionHandler(SortParametersNotValidException eventSortParamNotValidException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(eventSortParamNotValidException.getMessage())
                .time(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
