package ru.karaseva.sd.event.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Event {
    private static int nextId = 0;
    public final int subId;
    public final EventType eventType;
    public final Instant instant = Instant.now();

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                    .withLocale( Locale.UK )
                    .withZone( ZoneId.systemDefault() );

    public String getFormattedInstant() {
        return formatter.format(instant);
    }

    public Event(int subId, EventType eventType) {
        this.subId = subId;
        this.eventType = eventType;
    }

    public Event(EventType eventType) {
        this.subId = nextId;
        nextId++;
        this.eventType = eventType;
    }

    public int getSubId() {
        return subId;
    }

    @Override
    public String toString() {
        return "subId=" + subId + " " + eventType + " " + formatter.format(instant);
    }
}
