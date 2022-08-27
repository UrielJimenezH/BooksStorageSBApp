package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Book> getAll() {
        return repository.getAll();
    }

    public Book get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book add(Book book) {
        Book newBook = repository.save(book);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, book);
        return newBook;
    }

    public Book update(Long id, Book newBook) {
        newBook.setId(id);
        Optional<Book> updatedBook = repository.update(newBook);
        return updatedBook
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }
}
