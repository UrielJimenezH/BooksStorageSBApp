package com.example.booksStorage;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.domain.Letter;
import com.example.booksStorage.service.LetterService;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LetterServiceTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private LetterService service;
    @Mock
    private TypedQuery<Letter> query;
    private final String namedQuery = "query_find_all_letters";
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
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
        Mockito.when(entityManager.createNamedQuery(namedQuery, Letter.class)).thenReturn(query);

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        List<Letter> letters = List.of(letter);
        Mockito.when(query.getResultList()).thenReturn(letters);
        Mockito.when(entityManager.createNamedQuery(namedQuery, Letter.class)).thenReturn(query);


        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenLetterWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Letter.class, id)).thenReturn(null);

        service.get(id);
    }

    @Test
    public void get_ReturnsLetter_WhenLetterExists() {
        long id = 1;
        Mockito.when(entityManager.find(Letter.class, id)).thenReturn(letter);

        assertEquals(letter, service.get(id));
    }

    @Test
    public void add_ReturnsLetter() {
        assertNotNull(service.add(letter));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenLetterWasNotFound() {
        long letterId = 10L;
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(null);

        service.update(letterId, letter);
    }

    @Test
    public void update_ReturnsLetter_WhenLetterExists() {
        long letterId = 10L;
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(letter);

        assertEquals(letter, service.update(letterId, letter));
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookWasNotFound() {
        long id = 30;
        Mockito.when(entityManager.find(Letter.class, id)).thenReturn(null);

        service.delete(id);
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookExists() {
        long id = 3;
        Mockito.when(entityManager.find(Letter.class, id)).thenReturn(letter);

        service.delete(id);
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void hold_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        long holderId = 10L;
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(null);
        service.hold(6L, holderId);
    }

    @Test(expected = ElementAlreadyBeingHoldException.class)
    public void hold_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long currentHolderId = 3L;
        long letterId = 1L;
        long newHolderId = 1L;
        letter.setHolderId(currentHolderId);
        Mockito.when(entityManager.find(User.class, newHolderId)).thenReturn(user);
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(letter);
        service.hold(letterId, newHolderId);
    }

    @Test
    public void hold_ReturnsLetter_WhenLetterIsNotAlreadyBeingHold() {
        long letterId = 1L;
        long holderId = 1L;
        letter.setHolderId(null);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(letter);
        Mockito.when(entityManager.merge(letter)).thenReturn(letter);
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

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(letter);
        service.release(letterId, holderId);
    }

    @Test
    public void release_ReturnsLetter_WhenLetterIsNotAlreadyBeingHold() {
        long letterId = 1L;
        long holderId = 1L;
        letter.setHolderId(holderId);

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Letter.class, letterId)).thenReturn(letter);
        letter.setHolderId(null);
        Mockito.when(entityManager.merge(letter)).thenReturn(letter);
        Letter heldLetter = service.release(letterId, holderId);

        assertNull(heldLetter.getHolderId());
    }
}
