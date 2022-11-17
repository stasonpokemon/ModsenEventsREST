package com.modsen.app.controller;

import com.modsen.app.entity.dto.EventDTO;
import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.exception.EventNotValidException;
import com.modsen.app.exception.SortParametersNotValidException;
import com.modsen.app.service.EventService;
import com.modsen.app.util.SortRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<?> getAllEvents(@RequestParam(name = "sort", required = false) String[] sort) {
        ResponseEntity<?> response;
        try {
            List<EventDTO> eventsDTO;
            if (Optional.ofNullable(sort).isPresent()) {
                // sorting by sort params
                eventsDTO = eventService.findAll(SortRequest.by(Event.class, sort)).stream().map(event -> modelMapper.map(event, EventDTO.class)).collect(Collectors.toList());
            } else {
                // without sorting
                eventsDTO = eventService.findAll().stream().map(event -> modelMapper.map(event, EventDTO.class)).collect(Collectors.toList());
            }
            response = new ResponseEntity<List<EventDTO>>(eventsDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException eventSortParamNotValidException) {
            throw eventSortParamNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get events",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        ResponseEntity<?> response;
        try {
            EventDTO eventDTO = modelMapper.map(eventService.findById(id), EventDTO.class);
            response = new ResponseEntity<EventDTO>(modelMapper.map(eventDTO, EventDTO.class), HttpStatus.OK);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addNewEvent(@RequestBody @Valid EventDTO eventDTO,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
            });
            throw new EventNotValidException(errorMessage.toString());
        }
        ResponseEntity<?> response;
        try {
            Event savedEvent = eventService.save(modelMapper.map(eventDTO, Event.class));
            EventDTO savedEventDTO = modelMapper.map(savedEvent, EventDTO.class);
            response = new ResponseEntity<EventDTO>(savedEventDTO, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to save event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEventByPatch(@PathVariable("id") Long id,
                                                @RequestBody @Valid EventDTO eventDTO,
                                                BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            StringBuilder errorMessage = new StringBuilder();
//            bindingResult.getFieldErrors().forEach(fieldError -> {
//                errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
//            });
//            throw new EventNotValidException(errorMessage.toString());
//        }
        return updateEventHelper(id, eventDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEventByPut(@PathVariable("id") Long id,
                                              @RequestBody @Valid EventDTO eventDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
            });
            throw new EventNotValidException(errorMessage.toString());
        }
        return updateEventHelper(id, eventDTO);
    }

    private ResponseEntity<?> updateEventHelper(Long id, EventDTO eventDTO) {
        ResponseEntity<?> response;
        try {
            Event updatedEvent = eventService.update(id, modelMapper.map(eventDTO, Event.class));
            EventDTO updatedEventDTO = modelMapper.map(updatedEvent, EventDTO.class);
            response = new ResponseEntity<EventDTO>(updatedEventDTO, HttpStatus.RESET_CONTENT);
            System.out.println(updatedEventDTO);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to update event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        ResponseEntity<String> response;
        try {
            eventService.delete(id);
            response = new ResponseEntity<String>("Event " + id + " was deleted", HttpStatus.OK);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to delete event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
