package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
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

        this.title = validate(title);
        this.publisher = validate(publisher);
    }
}
