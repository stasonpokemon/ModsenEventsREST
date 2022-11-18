package com.modsen.app.dao;

import com.modsen.app.entity.Event;
import com.modsen.app.util.SortRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-events-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-events-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
class EventDAOImplTest {

    @Autowired
    private EventDAO eventDAO;

    @Test
    void findAll() {
        List<Event> events = eventDAO.findAll();
        assertEquals(3, events.size(), "Events size must be 3");
        assertNotEquals(4, events.size(), "Events size must not be 4");
    }

    @Test
    void findAllBySortRequest() {
        List<Event> events = eventDAO.findAll(SortRequest.by(Event.class, new String[]{"topic","asc"}));
        assertEquals(3, events.size(), "Events size must be 3");
        assertNotEquals(4, events.size(), "Events size must not be 4");
    }

    @Test
    void findById() {
        Event event = eventDAO.findById(1L).get();
        assertEquals(1, event.getId(), "Event id  must be 1");
        assertNotEquals(4, event.getId(), "Event id  must not be 4");
    }

    @Test
    void save() {
        // new event id must be 4 because previous event id in DB equals 3
        Event newEvent = new Event("Test topic", "Test description", "Test organizer", new Timestamp(new Date().getTime()), "Test location");
        newEvent.setCreatedAt(LocalDateTime.now());
        newEvent.setUpdatedAt(LocalDateTime.now());
        Event savedEvent = eventDAO.save(newEvent);
        assertEquals(4, savedEvent.getId(), "Saved event id must be 4");
        assertNotEquals(5, savedEvent.getId(), "Saved event id must not be 5");
    }

    @Test
    void update() {
        Event eventForUpdate = new Event("Test topic changed", "Test description changed", "Test organizer changed", new Timestamp(new Date().getTime()), "Test location changed");
        eventForUpdate.setId(1L);
        Event updatedEvent = eventDAO.update(eventForUpdate);
        assertEquals(eventDAO.findById(1L).get(), updatedEvent);
        assertNotEquals(eventDAO.findById(2L).get(), updatedEvent);
    }

    @Test
    void delete() {
        Event event = eventDAO.findById(2L).get();
        assertDoesNotThrow(() -> eventDAO.delete(event), "Wrong event");
        assertEquals(2, eventDAO.findAll().size(), "Events size after deleting must be 2 ");
        assertNotEquals(3, eventDAO.findAll().size(), "Events size after deleting must not be 3");
    }
}

