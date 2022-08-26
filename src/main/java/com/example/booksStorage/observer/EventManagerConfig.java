package com.example.booksStorage.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventManagerConfig<T> {
    public static final String BOOK_CREATION_EVENT = "bookCreated";
    public static final String MAGAZINE_CREATION_EVENT = "magazineCreated";
    public static final String NEWSPAPER_CREATION_EVENT = "newspaperCreated";
    public static final String LETTER_CREATION_EVENT = "letterCreated";

    private final Subscriber<T> subscriber;

    @Autowired
    public EventManagerConfig(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
    }

    public Map<String, List<Subscriber<T>>> getEvents() {
        Map<String, List<Subscriber<T>>> map = new HashMap<>();
        List.of(BOOK_CREATION_EVENT,
                MAGAZINE_CREATION_EVENT,
                NEWSPAPER_CREATION_EVENT,
                LETTER_CREATION_EVENT
        ).forEach(event -> map.put(event, List.of(subscriber)));

        return map;
    }
}

