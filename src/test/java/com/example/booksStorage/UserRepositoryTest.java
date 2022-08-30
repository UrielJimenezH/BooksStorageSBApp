package com.example.booksStorage;

import com.example.booksStorage.user.User;
import com.example.booksStorage.user.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

public class UserRepositoryTest {
    private static UserRepository repository;
    private final static User user = new User(
            "Carlos",
            "Lopez",
            "Manuel Perez # 23",
            LocalDate.now(),
            "carlos23",
            "carlos#1234"
    );

    @BeforeClass
    public static void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("users-test-data.sql")
                .build();

        repository = new UserRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", new JdbcTemplate(db));
    }

    @Test
    public void get_ReturnsUser_WhenTableHasRegistersAlready() {
        Optional<User> user = repository.get(1L);
        assertTrue(user.isPresent());
        assertEquals("Carlos", user.get().getName());
    }

    @Test
    public void get_ReturnsEmptyUser_WhenUserWithGivenIdDoesNotExists() {
        Optional<User> user = repository.get(32L);
        assertTrue(user.isEmpty());
    }

    @Test
    public void get_ReturnsEmptyUser_WhenUserWithGivenIdIsNull() {
        Optional<User> user = repository.get(null);
        assertTrue(user.isEmpty());
    }

    @Test
    public void getAll_ReturnsNonEmptyList() {
        List<User> users = repository.getAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void save_ReturnsUser() {
        User savedUser = repository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
    }

    @Test
    public void get_ReturnsUser_WhenPreviouslyInserted() {
        User savedUser = repository.save(user);

        Optional<User> user2 = repository.get(savedUser.getId());
        user2.ifPresentOrElse((b) -> {
            assertEquals(user.getName(), b.getName());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void update_ReturnsEmptyUser_WhenUserWithGivenIdDoesNotExists() {
        user.setId(50L);
        Optional<User> savedUser = repository.update(user);

        assertFalse(savedUser.isPresent());
    }

    @Test
    public void update_ReturnsEmptyUser_WhenUserWithGivenIdIsNull() {
        user.setId(null);
        Optional<User> savedUser = repository.update(user);

        assertFalse(savedUser.isPresent());
    }

    @Test
    public void update_ReturnsUser_WhenUserWithGivenIdExists() {
        user.setId(1L);
        Optional<User> savedUser = repository.update(user);

        savedUser.ifPresentOrElse((b) -> {
            assertEquals(user.getName(), b.getName());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void delete_ReturnsEmptyUser_WhenUserWithGivenIdDoesNotExists() {
        Optional<User> user = repository.delete(50L);

        assertFalse(user.isPresent());
    }

    @Test
    public void delete_ReturnsUser_WhenUserWithGivenIdIsExists() {
        Optional<User> user = repository.delete(6L);

        user.ifPresentOrElse((b) -> {
            assertNotNull(b.getName());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }
}
