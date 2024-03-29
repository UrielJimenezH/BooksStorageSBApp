package com.example.booksStorage.domain;

import com.example.booksStorage.validations.Validations;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Magazines")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(query = "SELECT m FROM Magazine m", name = "query_find_all_magazines")
@NoArgsConstructor
public final class Magazine extends Item {
    @Column(name = "title")
    private String title;
    @Column(name = "publisher")
    private String publisher;

    public Magazine(
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
