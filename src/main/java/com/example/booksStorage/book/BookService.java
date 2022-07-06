package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Repository<Long, Item> repository;

    @Autowired
    public BookService(@Qualifier("treeMapRepository") Repository<Long, Item> repository) {
        this.repository = repository;
    }

    public List<Book> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .collect(Collectors.toList());
    }

    public Optional<Book> get(Long bookId) {
        return repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item);
    }

    public Book add(Book book) {
        repository.save(book.getId(), book);
        return book;
    }

    public Optional<Book> update(Long bookId, Book newBook) {
        Optional<Book> book = repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item);

        if (book.isEmpty())
            return Optional.empty();

        newBook.setId(bookId);
        newBook.setRegistrationDate(book.get().getRegistrationDate());
        repository.update(bookId, newBook);
        return Optional.of(newBook);
    }

    public Optional<Book> delete(Long bookId) {
        Optional<Book> bookFound = repository.get(bookId)
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item);

        if (bookFound.isPresent())
            repository.delete(bookId);

        return bookFound;
    }
}
