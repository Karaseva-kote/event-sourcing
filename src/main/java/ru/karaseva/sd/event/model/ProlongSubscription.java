package ru.karaseva.sd.event.model;

import java.time.Duration;

public final class ProlongSubscription extends EventType {
    public final Duration duration;

    public ProlongSubscription(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ProlongSubscription: duration = " + duration.toDays() + " days";
    }
}
