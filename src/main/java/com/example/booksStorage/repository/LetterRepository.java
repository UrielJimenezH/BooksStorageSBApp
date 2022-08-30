package com.example.booksStorage.repository;

import com.example.booksStorage.domain.Letter;
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
public class LetterRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Letter> rowMapper = (rs, rowNum) -> {
        Letter letter = new Letter(
                rs.getString("summary"),
                rs.getInt("number_of_pages"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("author")
        );
        letter.setId((long) rs.getInt("id"));
        letter.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        letter.setHolderId(rs.getObject("holder_id", Long.class));

        return letter;
    };

    public Optional<Letter> get(Long id) {
        String sql = "SELECT * FROM Letters WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Letter> getAll() {
        String sql = "SELECT * FROM Letters";
        return jdbcTemplate.queryForStream(sql, rowMapper)
                .collect(Collectors.toList());
    }

    public Letter save(Letter letter) {
        String sql = "INSERT INTO Letters VALUES(NULL, ?, ?, ?, ?, ?, NULL)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, letter.getAuthor());
            statement.setString(2, letter.getSummary());
            statement.setInt(3, letter.getNumberOfPages());
            statement.setDate(4, Date.valueOf(letter.getReleaseDate()));
            statement.setDate(5, Date.valueOf(letter.getRegistrationDate()));

            return statement;
        }, keyHolder);

        letter.setId(keyHolder.getKey().longValue());
        return letter;
    }

    public Optional<Letter> update(Letter letter) {
        Optional<Letter> letterFound = get(letter.getId());
        letterFound.ifPresent((b) -> {
            String sql = "UPDATE Letters SET " +
                    "author = ?, " +
                    "summary = ?, " +
                    "number_of_pages = ?, " +
                    "release_date = ?, " +
                    "registration_date = ? " +
                    "WHERE id = ?";

            jdbcTemplate.update(sql,
                    letter.getAuthor(),
                    letter.getSummary(),
                    letter.getNumberOfPages(),
                    Date.valueOf(letter.getReleaseDate()),
                    Date.valueOf(letter.getRegistrationDate()),
                    letter.getId()
            );
        });


        if (letterFound.isPresent())
            return Optional.of(letter);
        else
            return letterFound;
    }

    public Optional<Letter> delete(long id) {
        Optional<Letter> letterFound = get(id);
        letterFound.ifPresent((b) -> {
            String sql = "DELETE FROM Letters WHERE id = ?";
            jdbcTemplate.update(sql, id);
        });
        return letterFound;
    }

    public Optional<Letter> hold(long letterId, long holderId) {
        Optional<Letter> letterFound = get(letterId);
        letterFound.ifPresent((b) -> {
            String sql = "UPDATE Letters SET " +
                    "holder_id = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, holderId, letterId);
            letterFound.get().setHolderId(holderId);
        });

        return letterFound;
    }

    public Optional<Letter> release(long letterId) {
        Optional<Letter> letterFound = get(letterId);
        letterFound.ifPresent((b) -> {
            String sql = "UPDATE Letters SET " +
                    "holder_id = NULL " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, letterId);
            letterFound.get().setHolderId(null);
        });

        return letterFound;
    }
}
