package ru.karaseva.sd.event.dao;

import ru.karaseva.sd.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventInMemoryDao implements EventDao {
//    private final List<Event> events = List.of(new Event(0, eventType), new Event(1, eventType));
    private final List<Event> events = new ArrayList<>();
    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public void addEvent(Event event) {
        events.add(event);
    }
}
