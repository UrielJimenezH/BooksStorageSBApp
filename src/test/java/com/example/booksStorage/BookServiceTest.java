package com.example.booksStorage;

import com.example.booksStorage.book.Book;
import com.example.booksStorage.book.BookRepository;
import com.example.booksStorage.book.BookService;
import com.example.booksStorage.observer.EventManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository repository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private BookService service;
    private final static Book book = new Book("Summary", 3, LocalDate.now(), "Title", "Author", "Publisher", "Edition");

    @Test
    public void getAll_ReturnsEmptyList() {
        Mockito.when(repository.getAll()).thenReturn(Collections.emptyList());

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        Mockito.when(repository.getAll()).thenReturn(List.of(book));

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsBook_WhenBookExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.get(id));
    }

    @Test
    public void add_ReturnsBook() {
        Mockito.when(repository.save(book)).thenReturn(book);

        assertNotNull(service.add(book));
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenBookWasNotFound() {
        Mockito.when(repository.update(book)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id 1 does not exist"));

        service.update(book.getId(), book);
    }

    @Test
    public void update_ReturnsBook_WhenBookExists() {
        Mockito.when(repository.update(book)).thenReturn(Optional.of(book));

        assertEquals(book, service.update(book.getId(), book));
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsBook_WhenBookExists() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.delete(id));
    }
}
