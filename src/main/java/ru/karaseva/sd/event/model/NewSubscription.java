package ru.karaseva.sd.event.model;

import java.time.Duration;

public final class NewSubscription extends EventType {

    public NewSubscription() {
    }

    @Override
    public String toString() {
        return "Create Subscription";
    }
}
