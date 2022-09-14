package com.example.booksStorage.repository;

import com.example.booksStorage.domain.Book;
import com.example.booksStorage.domain.Newspaper;
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
public class NewspaperRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Newspaper> rowMapper = (rs, rowNum) -> {
        Newspaper newspaper = new Newspaper(
                rs.getString("summary"),
                rs.getInt("number_of_pages"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("title"),
                rs.getString("publisher")
        );
        newspaper.setId((long) rs.getInt("id"));
        newspaper.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        newspaper.setHolderId(rs.getObject("holder_id", Long.class));

        return newspaper;
    };

    public Optional<Newspaper> get(Long id) {
        String sql = "SELECT * FROM Newspapers WHERE id = ?";
        List<Newspaper> newspapers = jdbcTemplate.query(sql, rowMapper, id);

        if (newspapers.isEmpty())
            return Optional.empty();
        else
            return newspapers.stream().findFirst();
    }

    public List<Newspaper> getAll() {
        String sql = "SELECT * FROM Newspapers";
        return jdbcTemplate.queryForStream(sql, rowMapper)
                .collect(Collectors.toList());
    }

    public Newspaper save(Newspaper newspaper) {
        String sql = "INSERT INTO Newspapers VALUES(NULL, ?, ?, ?, ?, ?, ?, NULL)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, newspaper.getTitle());
            statement.setString(2, newspaper.getPublisher());
            statement.setString(3, newspaper.getSummary());
            statement.setInt(4, newspaper.getNumberOfPages());
            statement.setDate(5, Date.valueOf(newspaper.getReleaseDate()));
            statement.setDate(6, Date.valueOf(newspaper.getRegistrationDate()));

            return statement;
        }, keyHolder);

        newspaper.setId(keyHolder.getKey().longValue());
        return newspaper;
    }

    public Optional<Newspaper> update(Newspaper newspaper) {
        Optional<Newspaper> newspaperFound = get(newspaper.getId());
        newspaperFound.ifPresent((b) -> {
            String sql = "UPDATE Newspapers SET " +
                    "title = ?, " +
                    "publisher = ?, " +
                    "summary = ?, " +
                    "number_of_pages = ?, " +
                    "release_date = ?, " +
                    "registration_date = ? " +
                    "WHERE id = ?";

            jdbcTemplate.update(sql,
                    newspaper.getTitle(),
                    newspaper.getPublisher(),
                    newspaper.getSummary(),
                    newspaper.getNumberOfPages(),
                    Date.valueOf(newspaper.getReleaseDate()),
                    Date.valueOf(newspaper.getRegistrationDate()),
                    newspaper.getId()
            );
        });

        if (newspaperFound.isPresent())
            return Optional.of(newspaper);
        else
            return newspaperFound;
    }

    public Optional<Newspaper> delete(long id) {
        Optional<Newspaper> newspaperFound = get(id);
        newspaperFound.ifPresent((b) -> {
            String sql = "DELETE FROM Newspapers WHERE id = ?";
            jdbcTemplate.update(sql, id);
        });

        return newspaperFound;
    }

    public Optional<Newspaper> hold(long newspaperId, long holderId) {
        Optional<Newspaper> newspaperFound = get(newspaperId);
        newspaperFound.ifPresent((b) -> {
            String sql = "UPDATE Newspapers SET " +
                    "holder_id = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, holderId, newspaperId);
            newspaperFound.get().setHolderId(holderId);
        });

        return newspaperFound;
    }

    public Optional<Newspaper> release(long newspaperId) {
        Optional<Newspaper> newspaperFound = get(newspaperId);
        newspaperFound.ifPresent((b) -> {
            String sql = "UPDATE Newspapers SET " +
                    "holder_id = NULL " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, newspaperId);
            newspaperFound.get().setHolderId(null);
        });

        return newspaperFound;
    }
}
