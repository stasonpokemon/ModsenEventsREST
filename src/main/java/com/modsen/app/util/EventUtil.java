package com.modsen.app.util;

import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotValidException;
import org.springframework.validation.BindingResult;

public class EventUtil {

    private static EventUtil instance;

    private EventUtil(){}

    public synchronized static EventUtil getInstance(){
        if (instance == null){
            instance = new EventUtil();
        }
        return instance;
    }
    public void copyNotNullEventValues(Event from, Event to) {
        if (from.getTopic() != null && !from.getTopic().isEmpty()) {
            to.setTopic(from.getTopic());
        }
        if (from.getDescription() != null && !from.getDescription().isEmpty()) {
            to.setDescription(from.getDescription());
        }
        if (from.getOrganizer() != null && !from.getOrganizer().isEmpty()) {
            to.setOrganizer(from.getOrganizer());
        }
        if (from.getLocation() != null && !from.getLocation().isEmpty()) {
            to.setLocation(from.getLocation());
        }
        if (from.getEventTime() != null) {
            to.setEventTime(from.getEventTime());
        }
    }

    public void checkEventBindingResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
            });
            throw new EventNotValidException(errorMessage.toString());
        }
    }

}
