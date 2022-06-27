package com.example.booksStorage.letters;

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
public final class Letter extends Item {
    private String author;

    public Letter(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String author
    ) {
        super(summary, numberOfPages, releaseDate);
        validateParams(author);

        this.author = author;
    }

    private void validateParams(String author) {
        requireNonNullParams(author);

        if (author.isBlank())
            throw new IllegalArgumentException();
    }
}
