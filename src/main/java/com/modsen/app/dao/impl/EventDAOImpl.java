package com.modsen.app.dao.impl;

import com.modsen.app.dao.EventDAO;
import com.modsen.app.entity.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Event> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Event", Event.class).getResultList();

    }


    @Override
    public Optional<Event> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Event.class, id));
    }

    @Override
    public Event save(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.save(event);
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.update(event);
        return event;
    }

    @Override
    public void delete(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(event);

    }
}
