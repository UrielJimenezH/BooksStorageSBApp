package com.example.booksStorage.observer;


public interface Publisher<T> {
    void subscribe(String event, Subscriber<T> subscriber);
    void unsubscribe(String event, Subscriber<T> subscriber);
    void notifySubscribers(String event, T data);
}
