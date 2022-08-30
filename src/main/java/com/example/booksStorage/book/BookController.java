package com.example.booksStorage.book;

import com.example.booksStorage.user.Holder;
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
        return ResponseEntity.ok(service.delete(bookId));
    }

    @PostMapping("{bookId}/hold")
    public ResponseEntity<Book> holdBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.hold(bookId, user.getHolderId()));
    }

    @PostMapping("{bookId}/release")
    public ResponseEntity<Book> releaseBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.release(bookId, user.getHolderId()));
    }
}
