package com.example.booksStorage.controller;

import com.example.booksStorage.converter.BookConverter;
import com.example.booksStorage.dto.BookDto;
import com.example.booksStorage.service.BookService;
import com.example.booksStorage.domain.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;
    @Autowired
    private BookConverter converter;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(
                converter.entityListToDtoList(service.getAll())
        );
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
         return ResponseEntity.ok(
                 converter.entityToDto(service.get(bookId))
         );
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.add(converter.dtoToEntity(book))
                )
        );
    }

    @PutMapping("{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody BookDto book
    ) {
        return ResponseEntity.ok(
                converter.entityToDto(
                        service.update(bookId, converter.dtoToEntity(book))
                )
        );
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
        service.delete(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{bookId}/hold")
    public ResponseEntity<BookDto> holdBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        BookDto book = converter.entityToDto(
                service.hold(bookId, user.getHolderId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("{bookId}/release")
    public ResponseEntity<BookDto> releaseBook(@PathVariable("bookId") Long bookId, @RequestBody Holder user) {
        BookDto book = converter.entityToDto(
                service.release(bookId, user.getHolderId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
