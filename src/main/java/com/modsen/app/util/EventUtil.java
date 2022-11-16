package com.modsen.app.util;

import com.modsen.app.entity.Event;

public abstract class EventUtil {

    public static void copyNotNullEventValues(Event from, Event to) {
        if (from.getTopic() != null && !from.getTopic().isEmpty()) {
            to.setTopic(from.getTopic());
        }
        if (from.getDescription() != null && !from.getDescription().isEmpty()) {
            to.setDescription(from.getDescription());
        }
        if (from.getOrganizer() != null && !from.getOrganizer().isEmpty()) {
            to.setOrganizer(from.getOrganizer());
        }
        if (from.getLocation() != null && !from.getLocation().isEmpty()) {
            to.setLocation(from.getLocation());
        }
        if (from.getEventTime() != null) {
            to.setEventTime(from.getEventTime());
        }
    }
}
