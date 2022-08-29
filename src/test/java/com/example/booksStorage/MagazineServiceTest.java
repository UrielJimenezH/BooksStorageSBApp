package com.example.booksStorage;

import com.example.booksStorage.magazine.Magazine;
import com.example.booksStorage.magazine.MagazineRepository;
import com.example.booksStorage.magazine.MagazineService;
import com.example.booksStorage.observer.EventManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MagazineServiceTest {
    @Mock
    private MagazineRepository repository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private MagazineService service;
    private final static Magazine magazine = new Magazine("Summary", 3, LocalDate.now(), "Animals magazine", "Publisher");

    @Test
    public void getAll_ReturnsEmptyList() {
        Mockito.when(repository.getAll()).thenReturn(Collections.emptyList());

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        Mockito.when(repository.getAll()).thenReturn(List.of(magazine));

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenMagazineWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsMagazine_WhenMagazineExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(magazine));

        assertEquals(magazine, service.get(id));
    }

    @Test
    public void add_ReturnsMagazine() {
        Mockito.when(repository.save(magazine)).thenReturn(magazine);

        assertNotNull(service.add(magazine));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenMagazineWasNotFound() {
        Mockito.when(repository.update(magazine)).thenThrow(new NoSuchElementFoundException("Magazine with id 1 does not exist"));

        service.update(magazine.getId(), magazine);
    }

    @Test
    public void update_ReturnsMagazine_WhenMagazineExists() {
        Mockito.when(repository.update(magazine)).thenReturn(Optional.of(magazine));

        assertEquals(magazine, service.update(magazine.getId(), magazine));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenMagazineWasNotFound() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenThrow(new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsMagazine_WhenMagazineExists() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(magazine));

        assertEquals(magazine, service.delete(id));
    }
}
