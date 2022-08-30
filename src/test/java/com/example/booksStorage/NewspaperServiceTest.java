package com.example.booksStorage;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.repository.NewspaperRepository;
import com.example.booksStorage.service.NewspaperService;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.repository.UserRepository;
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
public class NewspaperServiceTest {
    @Mock
    private NewspaperRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private NewspaperService service;
    private final static Newspaper newspaper = new Newspaper("Summary", 3, LocalDate.now(), "Latest newspaper", "Publisher");
    private final static User user = new User(
            "Fernando",
            "Lopez",
            "Manuel Perez # 23",
            LocalDate.now(),
            "carlos23",
            "carlos#1234"
    );

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


    @Test(expected = NoSuchElementFoundException.class)
    public void hold_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.hold(6L, 10L);
    }

    @Test(expected = ElementAlreadyBeingHoldException.class)
    public void hold_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        newspaper.setHolderId(3L);
        Mockito.when(userRepository.get(1L)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(1L)).thenReturn(Optional.of(newspaper));
        service.hold(1L, 1L);
    }

    @Test
    public void hold_ReturnsNewspaper_WhenNewspaperIsNotAlreadyBeingHold() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(null);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(newspaperId)).thenReturn(Optional.of(newspaper));
        newspaper.setHolderId(holderId);
        Mockito.when(repository.hold(newspaperId, holderId)).thenReturn(Optional.of(newspaper));
        Newspaper heldNewspaper = service.hold(newspaperId, holderId);

        assertEquals(holderId, heldNewspaper.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.release(6L, 10L);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(3L);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(newspaperId)).thenReturn(Optional.of(newspaper));
        service.release(newspaperId, holderId);
    }

    @Test
    public void release_ReturnsNewspaper_WhenNewspaperIsNotAlreadyBeingHold() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(holderId);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(newspaperId)).thenReturn(Optional.of(newspaper));
        newspaper.setHolderId(null);
        Mockito.when(repository.release(newspaperId)).thenReturn(Optional.of(newspaper));
        Newspaper heldNewspaper = service.release(newspaperId, holderId);

        assertNull(heldNewspaper.getHolderId());
    }
}
