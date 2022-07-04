package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import com.example.booksStorage.validations.Validations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
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

        this.title = Validations.validate(title);
        this.publisher = Validations.validate(publisher);
    }
}
