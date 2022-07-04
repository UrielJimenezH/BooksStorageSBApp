package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import com.example.booksStorage.validations.Validations;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
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

        this.title = Validations.validate(title);
        this.author = Validations.validate(author);
        this.publisher = Validations.validate(publisher);
        this.edition = Validations.validate(edition);
    }
}
