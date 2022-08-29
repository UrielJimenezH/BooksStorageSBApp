package com.example.booksStorage;

import com.example.booksStorage.letters.Letter;
import com.example.booksStorage.letters.LetterRepository;
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

public class LetterRepositoryTest {
    private static LetterRepository repository;
    private final Letter letter = new Letter("Summary", 3, LocalDate.now(),"Famous author");

    @BeforeClass
    public static void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("letters-test-data.sql")
                .build();

        repository = new LetterRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", new JdbcTemplate(db));
    }

    @Test
    public void get_ReturnsLetter_WhenTableHasRegistersAlready() {
        Optional<Letter> letter = repository.get(1L);
        assertTrue(letter.isPresent());
        assertEquals("Person 1", letter.get().getAuthor());
    }

    @Test
    public void get_ReturnsEmptyLetter_WhenLetterWithGivenIdDoesNotExists() {
        Optional<Letter> letter = repository.get(32L);
        assertTrue(letter.isEmpty());
    }

    @Test
    public void get_ReturnsEmptyLetter_WhenLetterWithGivenIdIsNull() {
        Optional<Letter> letter = repository.get(null);
        assertTrue(letter.isEmpty());
    }

    @Test
    public void getAll_ReturnsNonEmptyList() {
        List<Letter> letters = repository.getAll();
        assertFalse(letters.isEmpty());
    }

    @Test
    public void save_ReturnsLetter() {
        Letter savedLetter = repository.save(letter);

        assertNotNull(savedLetter.getId());
        assertEquals(letter.getAuthor(), savedLetter.getAuthor());
    }

    @Test
    public void get_ReturnsLetter_WhenPreviouslyInserted() {
        Letter savedLetter = repository.save(letter);

        Optional<Letter> letter2 = repository.get(savedLetter.getId());
        letter2.ifPresentOrElse((b) -> {
            assertEquals(letter.getAuthor(), b.getAuthor());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void update_ReturnsEmptyLetter_WhenLetterWithGivenIdDoesNotExists() {
        Letter letter = new Letter("Summary", 3, LocalDate.now(), "Author 32223");
        letter.setId(50L);
        Optional<Letter> savedLetter = repository.update(letter);

        assertFalse(savedLetter.isPresent());
    }

    @Test
    public void update_ReturnsEmptyLetter_WhenLetterWithGivenIdIsNull() {
        Optional<Letter> savedLetter = repository.update(letter);

        assertFalse(savedLetter.isPresent());
    }

    @Test
    public void update_ReturnsLetter_WhenLetterWithGivenIdIsExists() {
        letter.setId(5L);
        Optional<Letter> savedLetter = repository.update(letter);

        savedLetter.ifPresentOrElse((b) -> {
            assertEquals(letter.getAuthor(), b.getAuthor());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void delete_ReturnsEmptyLetter_WhenLetterWithGivenIdDoesNotExists() {
        Optional<Letter> letter = repository.delete(50L);

        assertFalse(letter.isPresent());
    }

    @Test
    public void delete_ReturnsLetter_WhenLetterWithGivenIdIsExists() {
        Optional<Letter> letter = repository.delete(6L);

        letter.ifPresentOrElse((b) -> {
            assertNotNull(b.getAuthor());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }
}
