package com.example.booksStorage;

import com.example.booksStorage.domain.Book;
import com.example.booksStorage.service.BookService;
import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
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
public class BookServiceTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private BookService service;
    @Mock
    private TypedQuery<Book> query;
    private final String namedQuery = "query_find_all_books";
    private final static Book book = new Book("Summary", 3, LocalDate.now(), "Title", "Author", "Publisher", "Edition");
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
        Mockito.when(entityManager.createNamedQuery(namedQuery, Book.class)).thenReturn(query);

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        List<Book> books = List.of(book);
        Mockito.when(query.getResultList()).thenReturn(books);
        Mockito.when(entityManager.createNamedQuery(namedQuery, Book.class)).thenReturn(query);

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Book.class, id)).thenReturn(null);

        service.get(id);
    }

    @Test
    public void get_ReturnsBook_WhenBookExists() {
        long id = 1;
        Mockito.when(entityManager.find(Book.class, id)).thenReturn(book);

        assertEquals(book, service.get(id));
    }

    @Test
    public void add_ReturnsBook() {
        assertNotNull(service.add(book));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenBookWasNotFound() {
        long bookId = 10L;
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(null);

        service.update(bookId, book);
    }

    @Test
    public void update_ReturnsBook_WhenBookExists() {
        long bookId = 10L;
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(book);

        assertEquals(book, service.update(bookId, book));
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(Book.class, id)).thenReturn(null);

        service.delete(id);
    }

    @Test
    public void delete_DoesNotThrowException_WhenBookExists() {
        long id = 1;
        Mockito.when(entityManager.find(Book.class, id)).thenReturn(book);

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
        long bookId = 1L;
        long newHolderId = 1L;
        book.setHolderId(currentHolderId);
        Mockito.when(entityManager.find(User.class, newHolderId)).thenReturn(user);
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(book);
        service.hold(bookId, newHolderId);
    }

    @Test
    public void hold_ReturnsBook_WhenBookIsNotAlreadyBeingHold() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(null);
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(book);
        Mockito.when(entityManager.merge(book)).thenReturn(book);
        Book heldBook = service.hold(bookId, holderId);

        assertEquals(holderId, heldBook.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        long holderId = 10L;
        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(null);
        service.release(6L, holderId);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(3L);

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(book);
        service.release(bookId, holderId);
    }

    @Test
    public void release_ReturnsBook_WhenBookIsNotAlreadyBeingHold() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(holderId);

        Mockito.when(entityManager.find(User.class, holderId)).thenReturn(user);
        Mockito.when(entityManager.find(Book.class, bookId)).thenReturn(book);
        book.setHolderId(null);
        Mockito.when(entityManager.merge(book)).thenReturn(book);
        Book heldBook = service.release(bookId, holderId);

        assertNull(heldBook.getHolderId());
    }
}
