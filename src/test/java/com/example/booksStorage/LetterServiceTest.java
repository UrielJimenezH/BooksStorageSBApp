package com.example.booksStorage;

import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.letters.Letter;
import com.example.booksStorage.letters.LetterRepository;
import com.example.booksStorage.letters.LetterService;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.user.User;
import com.example.booksStorage.user.UserRepository;
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
public class LetterServiceTest {
    @Mock
    private LetterRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private LetterService service;
    private final static Letter letter = new Letter("Summary", 3, LocalDate.now(), "Some Person");
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
        Mockito.when(repository.getAll()).thenReturn(List.of(letter));

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
        Mockito.when(repository.get(id)).thenReturn(Optional.of(letter));

        assertEquals(letter, service.get(id));
    }

    @Test
    public void add_ReturnsLetter() {
        Mockito.when(repository.save(letter)).thenReturn(letter);

        assertNotNull(service.add(letter));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenLetterWasNotFound() {
        Mockito.when(repository.update(letter)).thenThrow(new NoSuchElementFoundException("Letter with id 1 does not exist"));

        service.update(letter.getId(), letter);
    }

    @Test
    public void update_ReturnsLetter_WhenLetterExists() {
        Mockito.when(repository.update(letter)).thenReturn(Optional.of(letter));

        assertEquals(letter, service.update(letter.getId(), letter));
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
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(letter));

        assertEquals(letter, service.delete(id));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void hold_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.hold(6L, 10L);
    }

    @Test(expected = ElementAlreadyBeingHoldException.class)
    public void hold_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        letter.setHolderId(3L);
        Mockito.when(userRepository.get(1L)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(1L)).thenReturn(Optional.of(letter));
        service.hold(1L, 1L);
    }

    @Test
    public void hold_ReturnsLetter_WhenLetterIsNotAlreadyBeingHold() {
        long letterId = 1L;
        long holderId = 1L;
        letter.setHolderId(null);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(letterId)).thenReturn(Optional.of(letter));
        letter.setHolderId(holderId);
        Mockito.when(repository.hold(letterId, holderId)).thenReturn(Optional.of(letter));
        Letter heldLetter = service.hold(letterId, holderId);

        assertEquals(holderId, heldLetter.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.release(6L, 10L);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long letterId = 1L;
        long holderId = 1L;
        letter.setHolderId(3L);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(letterId)).thenReturn(Optional.of(letter));
        service.release(letterId, holderId);
    }

    @Test
    public void release_ReturnsLetter_WhenLetterIsNotAlreadyBeingHold() {
        long letterId = 1L;
        long holderId = 1L;
        letter.setHolderId(holderId);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(letterId)).thenReturn(Optional.of(letter));
        letter.setHolderId(null);
        Mockito.when(repository.release(letterId)).thenReturn(Optional.of(letter));
        Letter heldLetter = service.release(letterId, holderId);

        assertNull(heldLetter.getHolderId());
    }
}
