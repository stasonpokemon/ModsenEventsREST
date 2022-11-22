package com.modsen.app.service;

import com.modsen.app.dao.EventDAO;
import com.modsen.app.entity.Event;
import com.modsen.app.entity.dto.EventDTO;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.exception.SortParametersNotValidException;
import com.modsen.app.service.impl.EventService;
import com.modsen.app.util.EventUtil;
import com.modsen.app.util.SortRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<EventDTO> eventsDTO;
            if (Optional.ofNullable(sort).isPresent()) {
                eventsDTO = eventDAO.findAll(SortRequest.by(Event.class, sort)).stream().map(event -> modelMapper.map(event, EventDTO.class)).collect(Collectors.toList());
            } else {
                eventsDTO = eventDAO.findAll().stream().map(event -> modelMapper.map(event, EventDTO.class)).collect(Collectors.toList());
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


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {
        ResponseEntity<?> response;
        try {
            EventDTO eventDTO = modelMapper.map(checkAndGetOneEventById(id), EventDTO.class);
            response = new ResponseEntity<EventDTO>(modelMapper.map(eventDTO, EventDTO.class), HttpStatus.OK);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Event checkAndGetOneEventById(Long id) {
        return eventDAO.findById(id).orElseThrow(() -> new EventNotFoundException(new StringBuilder()
                .append("Event with id = ")
                .append(id)
                .append(" not found")
                .toString()));
    }

    @Override
    public ResponseEntity<?> save(EventDTO eventDTO) {
        ResponseEntity<?> response;
        try {
            Event eventBeforeSaving = modelMapper.map(eventDTO, Event.class);
            eventBeforeSaving.setCreatedAt(LocalDateTime.now());
            eventBeforeSaving.setUpdatedAt(LocalDateTime.now());
            Event savedEvent = eventDAO.save(eventBeforeSaving);
            EventDTO savedEventDTO = modelMapper.map(savedEvent, EventDTO.class);
            response = new ResponseEntity<EventDTO>(savedEventDTO, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to save event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<?> update(Long id, EventDTO eventDTO) {
        ResponseEntity<?> response;
        try {
            Event eventBeforeUpdating = checkAndGetOneEventById(id);
            EventUtil.getInstance().copyNotNullEventValues(modelMapper.map(eventDTO, Event.class), eventBeforeUpdating);
            eventBeforeUpdating.setUpdatedAt(LocalDateTime.now());
            EventDTO updatedEventDTO = modelMapper.map(eventDAO.update(eventBeforeUpdating), EventDTO.class);
            response = new ResponseEntity<EventDTO>(updatedEventDTO, HttpStatus.RESET_CONTENT);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to update event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        ResponseEntity<String> response;
        try {
            eventDAO.delete(checkAndGetOneEventById(id));
            response = new ResponseEntity<String>(new StringBuilder()
                    .append("Event ")
                    .append(id)
                    .append(" was deleted").toString(), HttpStatus.OK);
        } catch (EventNotFoundException eventException) {
            throw eventException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to delete event",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
