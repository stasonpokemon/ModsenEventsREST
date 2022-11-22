package com.modsen.app.dao;

import com.modsen.app.entity.Event;
import com.modsen.app.util.SortRequest;

import java.util.List;
import java.util.Optional;

public interface EventDAO {

    List<Event> findAll();

    Optional<Event> findById(Long id);

    Event save(Event event);

    Event update(Event event);

    void delete(Event event);

    List<Event> findAll(SortRequest sortRequest);
}
