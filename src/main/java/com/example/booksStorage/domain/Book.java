package com.example.booksStorage.domain;

import com.example.booksStorage.validations.Validations;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Books")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(query = "SELECT b FROM Book b", name = "query_find_all_books")
public final class Book extends Item {
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "edition")
    private String edition;

    public Book() {
        super();
    }

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
