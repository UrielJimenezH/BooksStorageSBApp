package com.example.booksStorage;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.domain.Magazine;
import com.example.booksStorage.service.MagazineService;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MagazineServiceTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private MagazineService service;
    @Mock
    private TypedQuery<Magazine> query;
    private final String namedQuery = "query_find_all_magazines";
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
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
        Mockito.when(entityManager.createNamedQuery(namedQuery, Magazine.class)).thenReturn(query);

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        List<Magazine> magazines = List.of(magazine);
        Mockito.when(query.getResultList()).thenReturn(magazines);
        Mockito.when(entityManager.createNamedQuery(namedQuery, Magazine.class)).thenReturn(query);

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenMagazineWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Magazine.class, id)).thenReturn(null);

        service.get(id);
    }

    @Test
    public void get_ReturnsMagazine_WhenMagazineExists() {
        long id = 1;
        Mockito.when(entityManager.find(Magazine.class, id)).thenReturn(magazine);

        assertEquals(magazine, service.get(id));
    }

    @Test
    public void add_ReturnsMagazine() {
        assertNotNull(service.add(magazine));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenMagazineWasNotFound() {
        long magazineId = 10L;
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(null);

        service.update(magazineId, magazine);
    }

    @Test
    public void update_ReturnsMagazine_WhenMagazineExists() {
        long magazineId = 10L;
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(magazine);

        assertEquals(magazine, service.update(magazineId, magazine));
    }

    @Test
    public void delete_DoesNotThrowException_WhenMagazineWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Magazine.class, id)).thenReturn(null);
        service.delete(id);
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookExists() {
        long id = 1;
        Mockito.when(entityManager.find(Magazine.class, id)).thenReturn(magazine);
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
        long magazineId = 1L;
        long newHolderId = 1L;
        magazine.setHolderId(currentHolderId);
        Mockito.when(entityManager.find(User.class, newHolderId)).thenReturn(user);
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(magazine);
        service.hold(magazineId, newHolderId);
    }

    @Test
    public void hold_ReturnsMagazine_WhenMagazineIsNotAlreadyBeingHold() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(null);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(magazine);
        Mockito.when(entityManager.merge(magazine)).thenReturn(magazine);
        Magazine heldMagazine = service.hold(magazineId, holderId);

        assertEquals(holderId, heldMagazine.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        long holderId = 10L;
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(null);
        service.release(6L, holderId);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(3L);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(magazine);
        service.release(magazineId, holderId);
    }

    @Test
    public void release_ReturnsMagazine_WhenMagazineIsNotAlreadyBeingHold() {
        long magazineId = 1L;
        long holderId = 1L;
        magazine.setHolderId(holderId);

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Magazine.class, magazineId)).thenReturn(magazine);
        magazine.setHolderId(null);
        Mockito.when(entityManager.merge(magazine)).thenReturn(magazine);
        Magazine heldMagazine = service.release(magazineId, holderId);

        assertNull(heldMagazine.getHolderId());
    }
}
