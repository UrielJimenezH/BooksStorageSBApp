package com.example.booksStorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(service.getAllBooks());
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
         Optional<Book> opBook = service.getBook(bookId);

         if (opBook.isPresent())
             return ResponseEntity.ok(opBook.get());

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id " + bookId + " does not exist");
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(service.addBook(book));
    }

    @PutMapping("{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody Book book
    ) {
        Optional<Book> opBook = service.updateBook(bookId, book);

        if (opBook.isPresent())
            return ResponseEntity.ok(opBook.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id " + bookId + " does not exist");
    }

    @DeleteMapping("{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        service.deleteBook(bookId);
    }
}
