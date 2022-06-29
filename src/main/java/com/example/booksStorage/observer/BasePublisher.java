package com.example.booksStorage.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePublisher<T> implements Publisher<T> {
    private final List<Subscriber<T>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(T data) {
        for (Subscriber<T> subscriber: subscribers)
            subscriber.update(data);
    }
}
