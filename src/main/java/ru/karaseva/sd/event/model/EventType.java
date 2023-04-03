package ru.karaseva.sd.event.model;

public abstract sealed class EventType permits NewSubscription, ProlongSubscription, Enter, Exit {
}
