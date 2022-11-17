package com.modsen.app.service.impl;


import com.modsen.app.entity.Event;
import com.modsen.app.util.SortRequest;

import java.util.List;

public interface EventService {


    List<Event> findAll();


    List<Event> findAll(SortRequest sortRequest);


    Event findById(Long id);

    Event save(Event event);


    Event update(Long id, Event eventFromJson);

    void delete(Long id);


}
