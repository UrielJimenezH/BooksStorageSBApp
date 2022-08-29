package com.example.booksStorage;

import com.example.booksStorage.magazine.Magazine;
import com.example.booksStorage.magazine.MagazineRepository;
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

public class MagazineRepositoryTest {
    private static MagazineRepository repository;
    private final Magazine magazine = new Magazine("Summary", 3, LocalDate.now(), "Animals Magazine", "Publisher");

    @BeforeClass
    public static void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("magazines-test-data.sql")
                .build();

        repository = new MagazineRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", new JdbcTemplate(db));
    }

    @Test
    public void get_ReturnsMagazine_WhenTableHasRegistersAlready() {
        Optional<Magazine> magazine = repository.get(1L);
        assertTrue(magazine.isPresent());
        assertEquals("Magazine 1", magazine.get().getTitle());
    }

    @Test
    public void get_ReturnsEmptyMagazine_WhenMagazineWithGivenIdDoesNotExists() {
        Optional<Magazine> magazine = repository.get(32L);
        assertTrue(magazine.isEmpty());
    }

    @Test
    public void get_ReturnsEmptyMagazine_WhenMagazineWithGivenIdIsNull() {
        Optional<Magazine> magazine = repository.get(null);
        assertTrue(magazine.isEmpty());
    }

    @Test
    public void getAll_ReturnsNonEmptyList() {
        List<Magazine> magazines = repository.getAll();
        assertFalse(magazines.isEmpty());
    }

    @Test
    public void save_ReturnsMagazine() {
        Magazine savedMagazine = repository.save(magazine);

        assertNotNull(savedMagazine.getId());
        assertEquals(magazine.getTitle(), savedMagazine.getTitle());
    }

    @Test
    public void get_ReturnsMagazine_WhenPreviouslyInserted() {
        Magazine savedMagazine = repository.save(magazine);

        Optional<Magazine> magazine2 = repository.get(savedMagazine.getId());
        magazine2.ifPresentOrElse((b) -> {
            assertEquals(magazine.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void update_ReturnsEmptyMagazine_WhenMagazineWithGivenIdDoesNotExists() {
        magazine.setId(50L);
        Optional<Magazine> savedMagazine = repository.update(magazine);

        assertFalse(savedMagazine.isPresent());
    }

    @Test
    public void update_ReturnsEmptyMagazine_WhenMagazineWithGivenIdIsNull() {
        Optional<Magazine> savedMagazine = repository.update(magazine);

        assertFalse(savedMagazine.isPresent());
    }

    @Test
    public void update_ReturnsMagazine_WhenMagazineWithGivenIdIsExists() {
        magazine.setId(5L);
        Optional<Magazine> savedMagazine = repository.update(magazine);

        savedMagazine.ifPresentOrElse((b) -> {
            assertEquals(magazine.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void delete_ReturnsEmptyMagazine_WhenMagazineWithGivenIdDoesNotExists() {
        Optional<Magazine> magazine = repository.delete(50L);

        assertFalse(magazine.isPresent());
    }

    @Test
    public void delete_ReturnsMagazine_WhenMagazineWithGivenIdIsExists() {
        Optional<Magazine> magazine = repository.delete(6L);

        magazine.ifPresentOrElse((b) -> {
            assertNotNull(b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }
}
