package com.example.booksStorage.dto;

import com.example.booksStorage.validations.Validations;
import lombok.*;
import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class BookDto extends ItemDto {
    private String title;
    private String author;
    private String publisher;
    private String edition;

    public BookDto(
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
