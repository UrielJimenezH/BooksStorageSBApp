package com.example.booksStorage.observer;

public interface Subscriber<T> {
    void update(T data);
}
