package com.example.booksStorage;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.domain.Magazine;
import com.example.booksStorage.repository.MagazineRepository;
import com.example.booksStorage.service.MagazineService;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.repository.UserRepository;
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
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MagazineServiceTest {
    @Mock
    private MagazineRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private MagazineService service;
    private final static Magazine magazine = new Magazine("Summary", 3, LocalDate.now(), "Animals magazine", "Publisher");
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


    @Test(expected = NoSuchElementFoundException.class)
    public void hold_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.hold(6L, 10L);
    }

    @Test(expected = ElementAlreadyBeingHoldException.class)
    public void hold_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        magazine.setHolderId(3L);
        Mockito.when(userRepository.get(1L)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(1L)).thenReturn(Optional.of(magazine));
        service.hold(1L, 1L);
    }

    @Test
    public void hold_ReturnsMagazine_WhenMagazineIsNotAlreadyBeingHold() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(null);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(magazineId)).thenReturn(Optional.of(magazine));
        magazine.setHolderId(holderId);
        Mockito.when(repository.hold(magazineId, holderId)).thenReturn(Optional.of(magazine));
        Magazine heldMagazine = service.hold(magazineId, holderId);

        assertEquals(holderId, heldMagazine.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.release(6L, 10L);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(3L);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(magazineId)).thenReturn(Optional.of(magazine));
        service.release(magazineId, holderId);
    }

    @Test
    public void release_ReturnsMagazine_WhenMagazineIsNotAlreadyBeingHold() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(holderId);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(magazineId)).thenReturn(Optional.of(magazine));
        magazine.setHolderId(null);
        Mockito.when(repository.release(magazineId)).thenReturn(Optional.of(magazine));
        Magazine heldMagazine = service.release(magazineId, holderId);

        assertNull(heldMagazine.getHolderId());
    }
}
