package com.modsen.app.controller;

import com.modsen.app.entity.dto.EventDTO;
import com.modsen.app.service.impl.EventService;
import com.modsen.app.util.EventUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<?> getAllEvents(@RequestParam(name = "sort", required = false) String[] sort) {
        return eventService.findAll(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        return eventService.findById(id);
    }



    @PostMapping
    public ResponseEntity<?> addNewEvent(@RequestBody @Valid EventDTO eventDTO,
                                         BindingResult bindingResult) {
        EventUtil.getInstance().checkEventBindingResult(bindingResult);
        return eventService.save(eventDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEventByPatch(@PathVariable("id") Long id,
                                                @RequestBody @Valid EventDTO eventDTO) {
        return eventService.update(id, eventDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEventByPut(@PathVariable("id") Long id,
                                              @RequestBody @Valid EventDTO eventDTO,
                                              BindingResult bindingResult) {
        EventUtil.getInstance().checkEventBindingResult(bindingResult);
        return eventService.update(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        return eventService.delete(id);
    }
}
