package com.example.booksStorage.dto;

import com.example.booksStorage.validations.*;
import lombok.*;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public abstract class ItemDto {
    private Long id;
    private String summary;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private LocalDate registrationDate;
    private Long holderId = null;

    public ItemDto(
            String summary,
            int numberOfPages,
            LocalDate releaseDate
    ) {
        this.summary = Validations.validate(summary);
        this.numberOfPages = Validations.validate(numberOfPages);
        this.releaseDate = Validations.validate(releaseDate);

        this.registrationDate = LocalDate.now();
    }
}
