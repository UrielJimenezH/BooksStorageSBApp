package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Repository<Long, Item> repository;
    private final EventManager<Item> eventManager;

    @Autowired
    public BookService(
            @Qualifier("treeMapRepository") Repository<Long, Item> repository,
            EventManager<Item> eventManager
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
    }

    public List<Book> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .collect(Collectors.toList());
    }

    public Book get(Long bookId) {
        return repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + bookId + " does not exist"));
    }

    public Book add(Book book) {
        repository.save(book.getId(), book);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, book);
        return book;
    }

    public Book update(Long bookId, Book newBook) {
        return repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> {
                    Book book = (Book) item;
                    newBook.setId(bookId);
                    newBook.setRegistrationDate(book.getRegistrationDate());
                    repository.update(bookId, newBook);
                    return newBook;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + bookId + " does not exist"));
    }

    public Book delete(Long bookId) {
        return repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> {
                    Book book = (Book) item;
                    repository.delete(bookId);
                    return book;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + bookId + " does not exist"));
    }
}
