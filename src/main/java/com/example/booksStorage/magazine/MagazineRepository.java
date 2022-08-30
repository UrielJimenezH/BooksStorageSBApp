package com.example.booksStorage.magazine;

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
public class MagazineRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Magazine> rowMapper = (rs, rowNum) -> {
        Magazine magazine = new Magazine(
                rs.getString("summary"),
                rs.getInt("number_of_pages"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("title"),
                rs.getString("publisher")
        );
        magazine.setId((long) rs.getInt("id"));
        magazine.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        magazine.setHolderId(rs.getObject("holder_id", Long.class));

        return magazine;
    };

    public Optional<Magazine> get(Long id) {
        String sql = "SELECT * FROM Magazines WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Magazine> getAll() {
        String sql = "SELECT * FROM Magazines";
        return jdbcTemplate.queryForStream(sql, rowMapper)
                .collect(Collectors.toList());
    }

    public Magazine save(Magazine magazine) {
        String sql = "INSERT INTO Magazines VALUES(NULL, ?, ?, ?, ?, ?, ?, NULL)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, magazine.getTitle());
            statement.setString(2, magazine.getPublisher());
            statement.setString(3, magazine.getSummary());
            statement.setInt(4, magazine.getNumberOfPages());
            statement.setDate(5, Date.valueOf(magazine.getReleaseDate()));
            statement.setDate(6, Date.valueOf(magazine.getRegistrationDate()));

            return statement;
        }, keyHolder);

        magazine.setId((Long) keyHolder.getKey());
        return magazine;
    }

    public Optional<Magazine> update(Magazine magazine) {
        Optional<Magazine> magazineFound = get(magazine.getId());
        magazineFound.ifPresent((b) -> {
            String sql = "UPDATE Magazines SET " +
                    "title = ?, " +
                    "publisher = ?, " +
                    "summary = ?, " +
                    "number_of_pages = ?, " +
                    "release_date = ?, " +
                    "registration_date = ? " +
                    "WHERE id = ?";

            jdbcTemplate.update(sql,
                    magazine.getTitle(),
                    magazine.getPublisher(),
                    magazine.getSummary(),
                    magazine.getNumberOfPages(),
                    Date.valueOf(magazine.getReleaseDate()),
                    Date.valueOf(magazine.getRegistrationDate()),
                    magazine.getId()
            );
        });

        if (magazineFound.isPresent())
            return Optional.of(magazine);
        else
            return magazineFound;
    }

    public Optional<Magazine> delete(long id) {
        Optional<Magazine> magazineFound = get(id);
        magazineFound.ifPresent((b) -> {
            String sql = "DELETE FROM Magazines WHERE id = ?";
            jdbcTemplate.update(sql, id);
        });

        return magazineFound;
    }

    public Optional<Magazine> hold(long magazineId, long holderId) {
        Optional<Magazine> magazineFound = get(magazineId);
        magazineFound.ifPresent((b) -> {
            String sql = "UPDATE Magazines SET " +
                    "holder_id = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, holderId, magazineId);
            magazineFound.get().setHolderId(holderId);
        });

        return magazineFound;
    }

    public Optional<Magazine> release(long magazineId) {
        Optional<Magazine> magazineFound = get(magazineId);
        magazineFound.ifPresent((b) -> {
            String sql = "UPDATE Magazines SET " +
                    "holder_id = NULL " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql, magazineId);
            magazineFound.get().setHolderId(null);
        });

        return magazineFound;
    }
}
