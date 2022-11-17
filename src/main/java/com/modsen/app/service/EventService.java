package com.modsen.app.service;

import com.modsen.app.dao.EventDAO;
import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.util.EventUtil;
import com.modsen.app.util.SortRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventDAO eventDAO;


    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventDAO.findAll();
    }


    @Transactional(readOnly = true)
    public List<Event> findAll(SortRequest sortRequest) {
        return eventDAO.findAll(sortRequest);
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Event findById(Long id) {
        return eventDAO.findById(id).orElseThrow(() -> new EventNotFoundException(new StringBuilder()
                .append("Event with id = ")
                .append(id)
                .append(" not found")
                .toString()));
    }

    public Event save(Event event) {
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return eventDAO.save(event);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Event update(Long id, Event eventFromJson) {
        Event eventFromDb = findById(id);
        EventUtil.copyNotNullEventValues(eventFromJson, eventFromDb);
        eventFromDb.setUpdatedAt(LocalDateTime.now());
        return eventDAO.update(eventFromDb);
    }

    public void delete(Long id) {
        eventDAO.delete(findById(id));
    }


}
