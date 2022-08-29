package com.example.booksStorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book(
                rs.getString("summary"),
                rs.getInt("number_of_pages"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getString("edition")
        );
        book.setId((long) rs.getInt("id"));
        book.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        book.setHolderId(rs.getObject("holder_id", Long.class));

        return book;
    };

    public Optional<Book> get(Long id) {
        String sql = "SELECT * FROM Books WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Book> getAll() {
        String sql = "SELECT * FROM Books";
        return jdbcTemplate.queryForStream(sql, rowMapper)
                .collect(Collectors.toList());
    }

    public Book save(Book book) {
        String sql = "INSERT INTO Books VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, book.getEdition());
            statement.setString(5, book.getSummary());
            statement.setInt(6, book.getNumberOfPages());
            statement.setDate(7, Date.valueOf(book.getReleaseDate()));
            statement.setDate(8, Date.valueOf(book.getRegistrationDate()));

            return statement;
        }, keyHolder);

        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    public Optional<Book> update(Book book) {
        Optional<Book> bookFound = get(book.getId());
        bookFound.ifPresent((b) -> {
            String sql = "UPDATE Books SET " +
                    "title = ?, " +
                    "author = ?, " +
                    "publisher = ?, " +
                    "edition = ?, " +
                    "summary = ?, " +
                    "number_of_pages = ?, " +
                    "release_date = ?, " +
                    "registration_date = ?, " +
                    "holder_id = ? " +
                    "WHERE id = ?";

            jdbcTemplate.update(sql,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getEdition(),
                    book.getSummary(),
                    book.getNumberOfPages(),
                    Date.valueOf(book.getReleaseDate()),
                    Date.valueOf(book.getRegistrationDate()),
                    book.getHolderId(),
                    book.getId()
            );
        });

        if (bookFound.isPresent())
            return Optional.of(book);
        else
            return bookFound;
    }

    public Optional<Book> delete(long id) {
        Optional<Book> bookFound = get(id);
        bookFound.ifPresent((b) -> {
            String sql = "DELETE FROM Books WHERE id = ?";
            jdbcTemplate.update(sql, id);
        });
        return bookFound;
    }
}
