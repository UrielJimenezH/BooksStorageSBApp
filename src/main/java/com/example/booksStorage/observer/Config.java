package com.example.booksStorage.observer;

import java.util.List;
import java.util.Map;

public interface Config<T> {
    Map<String, List<Subscriber<T>>> getEvents();
}