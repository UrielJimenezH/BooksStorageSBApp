package com.example.booksStorage;

import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.newspaper.Newspaper;
import com.example.booksStorage.newspaper.NewspaperRepository;
import com.example.booksStorage.newspaper.NewspaperService;
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
public class NewspaperServiceTest {
    @Mock
    private NewspaperRepository repository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private NewspaperService service;
    private final static Newspaper newspaper = new Newspaper("Summary", 3, LocalDate.now(), "Latest newspaper", "Publisher");

    @Test
    public void getAll_ReturnsEmptyList() {
        Mockito.when(repository.getAll()).thenReturn(Collections.emptyList());

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        Mockito.when(repository.getAll()).thenReturn(List.of(newspaper));

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenNewspaperWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsNewspaper_WhenNewspaperExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(newspaper));

        assertEquals(newspaper, service.get(id));
    }

    @Test
    public void add_ReturnsNewspaper() {
        Mockito.when(repository.save(newspaper)).thenReturn(newspaper);

        assertNotNull(service.add(newspaper));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenNewspaperWasNotFound() {
        Mockito.when(repository.update(newspaper)).thenThrow(new NoSuchElementFoundException("Newspaper with id 1 does not exist"));

        service.update(newspaper.getId(), newspaper);
    }

    @Test
    public void update_ReturnsNewspaper_WhenNewspaperExists() {
        Mockito.when(repository.update(newspaper)).thenReturn(Optional.of(newspaper));

        assertEquals(newspaper, service.update(newspaper.getId(), newspaper));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenNewspaperWasNotFound() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenThrow(new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsNewspaper_WhenNewspaperExists() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(newspaper));

        assertEquals(newspaper, service.delete(id));
    }
}
