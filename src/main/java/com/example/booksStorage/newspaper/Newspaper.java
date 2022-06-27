package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public final class Newspaper extends Item {
    private String title;
    private String publisher;

    public Newspaper(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String title,
            String publisher
    ) {
        super(summary, numberOfPages, releaseDate);
        validateParams(title, publisher);

        this.title = title;
        this.publisher = publisher;
    }

    private void validateParams(String title, String publisher) {
        requireNonNullParams(title, publisher);

        if (title.isBlank())
            throw new IllegalArgumentException();

        if (publisher.isBlank())
            throw new IllegalArgumentException();
    }
}
