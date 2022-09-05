package com.example.booksStorage;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.service.NewspaperService;
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
public class NewspaperServiceTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private NewspaperService service;
    @Mock
    private TypedQuery<Newspaper> query;
    private final String namedQuery = "query_find_all_newspapers";
    private final static Newspaper newspaper = new Newspaper("Summary", 3, LocalDate.now(), "Animals newspaper", "Publisher");
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
        Mockito.when(entityManager.createNamedQuery(namedQuery, Newspaper.class)).thenReturn(query);

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        List<Newspaper> newspapers = List.of(newspaper);
        Mockito.when(query.getResultList()).thenReturn(newspapers);
        Mockito.when(entityManager.createNamedQuery(namedQuery, Newspaper.class)).thenReturn(query);

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenNewspaperWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Newspaper.class, id)).thenReturn(null);

        service.get(id);
    }

    @Test
    public void get_ReturnsNewspaper_WhenNewspaperExists() {
        long id = 1;
        Mockito.when(entityManager.find(Newspaper.class, id)).thenReturn(newspaper);

        assertEquals(newspaper, service.get(id));
    }

    @Test
    public void add_ReturnsNewspaper() {
        assertNotNull(service.add(newspaper));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenNewspaperWasNotFound() {
        long newspaperId = 10L;
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(null);

        service.update(newspaperId, newspaper);
    }

    @Test
    public void update_ReturnsNewspaper_WhenNewspaperExists() {
        long newspaperId = 10L;
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(newspaper);

        assertEquals(newspaper, service.update(newspaperId, newspaper));
    }

    @Test
    public void delete_DoesNotThrowException_WhenNewspaperWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Newspaper.class, id)).thenReturn(null);
        service.delete(id);
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookExists() {
        long id = 1;
        Mockito.when(entityManager.find(Newspaper.class, id)).thenReturn(newspaper);
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
        long newspaperId = 1L;
        long newHolderId = 1L;
        newspaper.setHolderId(currentHolderId);
        Mockito.when(entityManager.find(User.class, newHolderId)).thenReturn(user);
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(newspaper);
        service.hold(newspaperId, newHolderId);
    }

    @Test
    public void hold_ReturnsNewspaper_WhenNewspaperIsNotAlreadyBeingHold() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(null);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(newspaper);
        Mockito.when(entityManager.merge(newspaper)).thenReturn(newspaper);
        Newspaper heldNewspaper = service.hold(newspaperId, holderId);

        assertEquals(holderId, heldNewspaper.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        long holderId = 10L;
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(null);
        service.release(6L, holderId);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(3L);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(newspaper);
        service.release(newspaperId, holderId);
    }

    @Test
    public void release_ReturnsNewspaper_WhenNewspaperIsNotAlreadyBeingHold() {
        long newspaperId = 1L;
        long holderId = 1L;
        newspaper.setHolderId(holderId);

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Newspaper.class, newspaperId)).thenReturn(newspaper);
        newspaper.setHolderId(null);
        Mockito.when(entityManager.merge(newspaper)).thenReturn(newspaper);
        Newspaper heldNewspaper = service.release(newspaperId, holderId);

        assertNull(heldNewspaper.getHolderId());
    }
}
