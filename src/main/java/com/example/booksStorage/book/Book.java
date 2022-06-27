package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public final class Book extends Item {
    private String title;
    private String author;
    private String publisher;
    private String edition;

    public Book(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String title,
            String author,
            String publisher,
            String edition
    ) {
        super(summary, numberOfPages, releaseDate);
        validateParams(title, author, publisher, edition);

        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.edition = edition;
    }

    private void validateParams(String title, String author, String publisher, String edition) {
        requireNonNullParams(title, author, publisher, edition);

        if (title.isBlank())
            throw new IllegalArgumentException();

        if (author.isBlank())
            throw new IllegalArgumentException();

        if (publisher.isBlank())
            throw new IllegalArgumentException();

        if (edition.isBlank())
            throw new IllegalArgumentException();
    }
}

//curl -X POST localhost:8080/books -H "Content-Type: application/json" -d '{"summary": "Some value", "numberOfPages": 3, "releaseDate": "1998-05-23", "registrationDate": "1998-05-23", "title": "Some value", "author": "Some value", "publisher": "Some value", "edition": "Some value"}'
