package com.modsen.app.service;

import com.modsen.app.dao.EventDAO;
import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public List<Event> findAll(String[] sortRequest) {
        Map<String, String> sortRequestMap = new LinkedHashMap<>();
        // if sortRequest contains ',' it means the sort array has more than one sort request
        if (sortRequest[0].contains(",")) {
            // sorting by more than one field
            for (String singleRequest : sortRequest) {
                String[] splitSingleRequest = singleRequest.split(",");
                sortRequestMap.put(splitSingleRequest[0], splitSingleRequest[1]);
            }
        } else {
            // sorting by one field
            sortRequestMap.put(sortRequest[0], sortRequest[1]);
        }
        return  eventDAO.findAll(sortRequestMap);
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
