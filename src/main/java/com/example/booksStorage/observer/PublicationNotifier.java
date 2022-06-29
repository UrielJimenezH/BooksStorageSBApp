package com.example.booksStorage.observer;

import org.springframework.stereotype.Component;

@Component
public class PublicationNotifier<T> implements Subscriber<T> {
    @Override
    public void update(T data) {
        System.out.println("--- Sending notification ---");
        System.out.println("New item published");
        System.out.println(data);
    }
}
