package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceStub {
    private final Map<Long, Item> books =  new HashMap<>();

    public List<Book> getAllBooks() {
        return books.values()
                .stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .collect(Collectors.toList());
    }

    public Optional<Book> getBook(Long bookId) {
        return Optional.ofNullable(books.get(bookId))
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item);
    }

    public Book addBook(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    public Optional<Book> updateBook(Long bookId, Book newBook) {
        Optional<Book> book = Optional.ofNullable(books.get(bookId))
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item);

        if (book.isEmpty())
            return Optional.empty();

        newBook.setId(bookId);
        newBook.setRegistrationDate(book.get().getRegistrationDate());
        books.put(bookId, newBook);
        return Optional.of(newBook);
    }

    public void deleteBook(Long bookId) {
        books.remove(bookId);
    }
}
