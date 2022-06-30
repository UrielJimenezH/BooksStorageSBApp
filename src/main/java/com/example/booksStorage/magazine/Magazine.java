package com.example.booksStorage.magazine;

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
public final class Magazine extends Item {
    private String title;
    private final String publisher;

    public Magazine(
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
