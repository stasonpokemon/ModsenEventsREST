package com.modsen.app.util;

import com.modsen.app.entity.Event;

public abstract class EventUtil {

    public static void copyNotNullEventValues(Event from, Event to) {
        if (from.getTopic() != null) {
            to.setTopic(from.getTopic());
        }
        if (from.getDescription() != null) {
            to.setDescription(from.getDescription());
        }
        if (from.getOrganizer() != null) {
            to.setOrganizer(from.getOrganizer());
        }
        if (from.getLocation() != null) {
            to.setLocation(from.getLocation());
        }
        if (from.getEventTime() != null) {
            to.setEventTime(from.getEventTime());
        }
    }
}
