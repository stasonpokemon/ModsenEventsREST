package com.modsen.app.controller;

import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;


    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        ResponseEntity<?> response;
        try {
            List<Event> events = eventService.findAll();
            response = new ResponseEntity<List<Event>>(events, HttpStatus.OK);
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
            Event event = eventService.findById(id);
            response = new ResponseEntity<Event>(event, HttpStatus.OK);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addNewEvent(@RequestBody Event event) {
        ResponseEntity<?> response;
        try {
            Event savedEvent = eventService.save(event);
            response = new ResponseEntity<Event>(savedEvent, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to save event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id,
                                         @RequestBody Event eventFromJson) {
        ResponseEntity<?> response;
        try {
            Event updatedEvent = eventService.update(id, eventFromJson);
            System.out.println(updatedEvent);
            response = new ResponseEntity<Event>(updatedEvent, HttpStatus.RESET_CONTENT);
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
