package com.example.booksStorage;

import com.example.booksStorage.domain.Book;
import com.example.booksStorage.repository.BookRepository;
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

public class BookRepositoryTest {
    private static BookRepository repository;
    private final Book book = new Book("Summary", 3, LocalDate.now(), "Easy math", "Author", "Publisher", "Edition");

    @BeforeClass
    public static void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("books-test-data.sql")
                .addScript("users-test-data.sql")
                .build();

        repository = new BookRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", new JdbcTemplate(db));
    }

    @Test
    public void get_ReturnsBook_WhenTableHasRegistersAlready() {
        Optional<Book> book = repository.get(1L);
        assertTrue(book.isPresent());
        assertEquals("Math Book 1", book.get().getTitle());
    }

    @Test
    public void get_ReturnsEmptyBook_WhenBookWithGivenIdDoesNotExists() {
        Optional<Book> book = repository.get(32L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void get_ReturnsEmptyBook_WhenBookWithGivenIdIsNull() {
        Optional<Book> book = repository.get(null);
        assertTrue(book.isEmpty());
    }

    @Test
    public void getAll_ReturnsNonEmptyList() {
        List<Book> books = repository.getAll();
        assertFalse(books.isEmpty());
    }

    @Test
    public void save_ReturnsBook() {
        Book savedBook = repository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
    }

    @Test
    public void get_ReturnsBook_WhenPreviouslyInserted() {
        Book savedBook = repository.save(book);

        Optional<Book> book2 = repository.get(savedBook.getId());
        book2.ifPresentOrElse((b) -> {
            assertEquals(book.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void update_ReturnsEmptyBook_WhenBookWithGivenIdDoesNotExists() {
        book.setId(50L);
        Optional<Book> savedBook = repository.update(book);

        assertFalse(savedBook.isPresent());
    }

    @Test
    public void update_ReturnsEmptyBook_WhenBookWithGivenIdIsNull() {
        Optional<Book> savedBook = repository.update(book);

        assertFalse(savedBook.isPresent());
    }

    @Test
    public void update_ReturnsBook_WhenBookWithGivenIdIsExists() {
        book.setId(5L);
        Optional<Book> savedBook = repository.update(book);

        savedBook.ifPresentOrElse((b) -> {
            assertEquals(book.getTitle(), b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }


    @Test
    public void delete_ReturnsEmptyBook_WhenBookWithGivenIdDoesNotExists() {
        Optional<Book> book = repository.delete(50L);

        assertFalse(book.isPresent());
    }

    @Test
    public void delete_ReturnsBook_WhenBookWithGivenIdIsExists() {
        Optional<Book> book = repository.delete(6L);

        book.ifPresentOrElse((b) -> {
            assertNotNull(b.getTitle());
        }, () -> {
            throw new RuntimeException("Not found");
        });
    }

    @Test
    public void hold_ReturnsEmptyOptional_WhenBookWithGivenIdDoesNotExist() {
        Optional<Book> bookFound = repository.hold(60L, 1L);
        assertFalse(bookFound.isPresent());
    }

    @Test
    public void hold_ReturnsABook_WhenBookAndUserWithGivenIdsExist() {
        long holderId = 2L;
        Optional<Book> bookFound = repository.hold(5L, holderId);
        assertTrue(bookFound.isPresent());
        assertEquals(holderId, bookFound.get().getHolderId().longValue());
    }

    @Test
    public void release_ReturnsEmptyOptional_WhenBookWithGivenIdDoesNotExist() {
        Optional<Book> bookFound = repository.release(60L);
        assertFalse(bookFound.isPresent());
    }

    @Test
    public void release_ReturnsABook_WhenBookWithGivenIdExists() {
        Optional<Book> bookFound = repository.release(5L);
        assertTrue(bookFound.isPresent());
        assertNull(bookFound.get().getHolderId());
    }
}
