package com.example.booksStorage;

import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.letters.Letter;
import com.example.booksStorage.letters.LetterRepository;
import com.example.booksStorage.letters.LetterService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class LetterServiceTest {
    @Mock
    private LetterRepository repository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private LetterService service;
    private final static Letter book = new Letter("Summary", 3, LocalDate.now(), "Some Person");

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

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenLetterWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new NoSuchElementFoundException("Letter with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsLetter_WhenLetterExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.get(id));
    }

    @Test
    public void add_ReturnsLetter() {
        Mockito.when(repository.save(book)).thenReturn(book);

        assertNotNull(service.add(book));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenLetterWasNotFound() {
        Mockito.when(repository.update(book)).thenThrow(new NoSuchElementFoundException("Letter with id 1 does not exist"));

        service.update(book.getId(), book);
    }

    @Test
    public void update_ReturnsLetter_WhenLetterExists() {
        Mockito.when(repository.update(book)).thenReturn(Optional.of(book));

        assertEquals(book, service.update(book.getId(), book));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenLetterWasNotFound() {
        long id = 30;
        Mockito.when(repository.delete(id)).thenThrow(new NoSuchElementFoundException("Letter with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsLetter_WhenLetterExists() {
        long id = 3;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.delete(id));
    }
}
