package com.example.booksStorage.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EventManager<T> implements Publisher<T> {
    private final Map<String, List<Subscriber<T>>> events;

    @Autowired
    public EventManager(EventManagerConfig<T> config) {
        this.events = config.getEvents();
    }

    @Override
    public void subscribe(String event, Subscriber<T> subscriber) {
        Optional.ofNullable(events.get(event))
                .ifPresentOrElse(
                        subscribers -> subscribers.add(subscriber),
                        () -> events.put(event, new ArrayList<>(List.of(subscriber)))
                );
    }

    @Override
    public void unsubscribe(String event, Subscriber<T> subscriber) {
        Optional.ofNullable(events.get(event))
                .ifPresent(subscribers -> subscribers.remove(subscriber));

    }

    @Override
    public void notifySubscribers(String event, T data) {
        Optional.ofNullable(events.get(event))
                .ifPresent(subscribers ->
                        subscribers.forEach(subscriber -> subscriber.update(data))
                );
    }
}
