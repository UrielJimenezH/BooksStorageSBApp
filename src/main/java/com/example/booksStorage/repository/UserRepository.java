package com.example.booksStorage.repository;

import com.example.booksStorage.domain.User;
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
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User(
                rs.getString("name"),
                rs.getString("lastname"),
                rs.getString("address"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("username"),
                rs.getString("password")
        );
        user.setId((long) rs.getInt("id"));
        user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());

        return user;
    };

    public Optional<User> get(Long id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.queryForStream(sql, rowMapper)
                .collect(Collectors.toList());
    }

    public User save(User user) {
        String sql = "INSERT INTO Users VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getAddress());
            statement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPassword());
            statement.setDate(7, Date.valueOf(user.getRegistrationDate()));

            return statement;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public Optional<User> update(User user) {
        Optional<User> userFound = get(user.getId());
        userFound.ifPresent((b) -> {
            String sql = "UPDATE Users SET " +
                    "name = ?, " +
                    "lastname = ?, " +
                    "address = ?, " +
                    "date_of_birth = ?, " +
                    "username = ?, " +
                    "password = ? " +
                    "WHERE id = ?";

            jdbcTemplate.update(sql,
                    user.getName(),
                    user.getLastname(),
                    user.getAddress(),
                    Date.valueOf(user.getDateOfBirth()),
                    user.getUsername(),
                    user.getPassword(),
                    user.getId()
            );
        });

        if (userFound.isPresent())
            return Optional.of(user);
        else
            return userFound;
    }

    public Optional<User> delete(long id) {
        Optional<User> userFound = get(id);
        userFound.ifPresent((b) -> {
            String sql = "DELETE FROM Users WHERE id = ?";
            jdbcTemplate.update(sql, id);
        });
        return userFound;
    }
}

