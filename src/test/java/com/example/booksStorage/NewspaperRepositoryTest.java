package com.example.booksStorage;

import com.example.booksStorage.newspaper.Newspaper;
import com.example.booksStorage.newspaper.NewspaperRepository;
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

public class NewspaperRepositoryTest {
    private static NewspaperRepository repository;
    private final Newspaper newspaper = new Newspaper("Summary", 3, LocalDate.now(), "Technology Newspaper", "Publisher");

    @BeforeClass
    public static void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("newspapers-test-data.sql")
                .addScript("users-test-data.sql")
                .build();

        repository = new NewspaperRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", new JdbcTemplate(db));
    }

    @Test
    public void get_ReturnsNewspaper_WhenTableHasRegistersAlready() {
        Optional<Newspaper> newspaper = repository.get(1L);
        assertTrue(newspaper.isPresent());
        assertEquals("Newspaper 1", newspaper.get().getTitle());
    }

    @Test
    public void get_ReturnsEmptyNewspaper_WhenNewspaperWithGivenIdDoesNotExists() {
        Optional<Newspaper> newspaper = repository.get(32L);
        assertTrue(newspaper.isEmpty());
    }

    @Test
    public void get_ReturnsEmptyNewspaper_WhenNewspaperWithGivenIdIsNull() {
        Optional<Newspaper> newspaper = repository.get(null);
        assertTrue(newspaper.isEmpty());
    }

    @Test
    public void getAll_ReturnsNonEmptyList() {
        List<Newspaper> newspapers = repository.getAll();
        assertFalse(newspapers.isEmpty());
    }

    @Test
    public void save_ReturnsNewspaper() {
        Newspaper savedNewspaper = repository.save(newspaper);

        assertNotNull(savedNewspaper.getId());
        assertEquals(newspaper.getTitle(), savedNewspaper.getTitle());
    }

    @Test
    public void get_ReturnsNewspaper_WhenPreviouslyInserted() {
        Newspaper savedNewspaper = repository.save(newspaper);

        Optional<Newspaper> newspaper2 = repository.get(savedNewspaper.getId());
        newspaper2.ifPresentOrElse((b) -> {
            assertEquals(newspaper.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void update_ReturnsEmptyNewspaper_WhenNewspaperWithGivenIdDoesNotExists() {
        newspaper.setId(50L);
        Optional<Newspaper> savedNewspaper = repository.update(newspaper);

        assertFalse(savedNewspaper.isPresent());
    }

    @Test
    public void update_ReturnsEmptyNewspaper_WhenNewspaperWithGivenIdIsNull() {
        Optional<Newspaper> savedNewspaper = repository.update(newspaper);

        assertFalse(savedNewspaper.isPresent());
    }

    @Test
    public void update_ReturnsNewspaper_WhenNewspaperWithGivenIdIsExists() {
        newspaper.setId(5L);
        Optional<Newspaper> savedNewspaper = repository.update(newspaper);

        savedNewspaper.ifPresentOrElse((b) -> {
            assertEquals(newspaper.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void delete_ReturnsEmptyNewspaper_WhenNewspaperWithGivenIdDoesNotExists() {
        Optional<Newspaper> newspaper = repository.delete(50L);

        assertFalse(newspaper.isPresent());
    }

    @Test
    public void delete_ReturnsNewspaper_WhenNewspaperWithGivenIdIsExists() {
        Optional<Newspaper> newspaper = repository.delete(6L);

        newspaper.ifPresentOrElse((b) -> {
            assertNotNull(b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void hold_ReturnsEmptyOptional_WhenNewspaperWithGivenIdDoesNotExist() {
        Optional<Newspaper> newspaperFound = repository.hold(60L, 1L);
        assertFalse(newspaperFound.isPresent());
    }

    @Test
    public void hold_ReturnsANewspaper_WhenNewspaperAndUserWithGivenIdsExist() {
        long holderId = 2L;
        Optional<Newspaper> newspaperFound = repository.hold(5L, holderId);
        assertTrue(newspaperFound.isPresent());
        assertEquals(holderId, newspaperFound.get().getHolderId().longValue());
    }

    @Test
    public void release_ReturnsEmptyOptional_WhenNewspaperWithGivenIdDoesNotExist() {
        Optional<Newspaper> newspaperFound = repository.release(60L);
        assertFalse(newspaperFound.isPresent());
    }

    @Test
    public void release_ReturnsANewspaper_WhenNewspaperWithGivenIdExists() {
        Optional<Newspaper> newspaperFound = repository.release(5L);
        assertTrue(newspaperFound.isPresent());
        assertNull(newspaperFound.get().getHolderId());
    }
}
