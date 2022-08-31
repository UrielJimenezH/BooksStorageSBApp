package com.example.booksStorage.controller;

import com.example.booksStorage.service.BookService;
import com.example.booksStorage.domain.Book;
import com.example.booksStorage.domain.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
         return ResponseEntity.ok(service.get(bookId));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(book));
    }

    @PutMapping("{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody Book book
    ) {
        return ResponseEntity.ok(service.update(bookId, book));
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
        service.delete(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{bookId}/hold")
    public ResponseEntity<Book> holdBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        Book book = service.hold(bookId, user.getHolderId());
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("{bookId}/release")
    public ResponseEntity<Book> releaseBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        Book book = service.release(bookId, user.getHolderId());
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}