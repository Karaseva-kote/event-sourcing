package ru.karaseva.sd.event.dao;

import ru.karaseva.sd.event.model.Event;

import java.util.List;

public interface EventDao {
    List<Event> getEvents();
    void addEvent(Event event);
}
