package com.example.booksStorage;

import com.example.booksStorage.domain.Book;
import com.example.booksStorage.repository.BookRepository;
import com.example.booksStorage.service.BookService;
import com.example.booksStorage.domain.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
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
public class BookServiceTest {
    @Mock
    private BookRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventManager<Item> eventManager;
    @InjectMocks
    private BookService service;
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
        Mockito.when(repository.getAll()).thenReturn(Collections.emptyList());

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        Mockito.when(repository.getAll()).thenReturn(List.of(book));

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsBook_WhenBookExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.get(id));
    }

    @Test
    public void add_ReturnsBook() {
        Mockito.when(repository.save(book)).thenReturn(book);

        assertNotNull(service.add(book));
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenBookWasNotFound() {
        Mockito.when(repository.update(book)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id 1 does not exist"));

        service.update(book.getId(), book);
    }

    @Test
    public void update_ReturnsBook_WhenBookExists() {
        Mockito.when(repository.update(book)).thenReturn(Optional.of(book));

        assertEquals(book, service.update(book.getId(), book));
    }

    @Test(expected = com.example.booksStorage.exceptionshandling.NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenBookWasNotFound() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenThrow(new com.example.booksStorage.exceptionshandling.NoSuchElementFoundException("Book with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsBook_WhenBookExists() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(book));

        assertEquals(book, service.delete(id));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void hold_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.hold(6L, 10L);
    }

    @Test(expected = ElementAlreadyBeingHoldException.class)
    public void hold_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        book.setHolderId(3L);
        Mockito.when(userRepository.get(1L)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(1L)).thenReturn(Optional.of(book));
        service.hold(1L, 1L);
    }

    @Test
    public void hold_ReturnsBook_WhenBookIsNotAlreadyBeingHold() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(null);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(bookId)).thenReturn(Optional.of(book));
        book.setHolderId(holderId);
        Mockito.when(repository.hold(bookId, holderId)).thenReturn(Optional.of(book));
        Book heldBook = service.hold(bookId, holderId);

        assertEquals(holderId, heldBook.getHolderId().longValue());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void release_ThrowsNoSuchElementFoundException_WhenUserWithGivenIdDoesNotExist() {
        service.release(6L, 10L);
    }

    @Test(expected = CanNotReleaseException.class)
    public void release_ThrowsElementAlreadyBeingHoldException_WhenHolderIdIsDifferentFromTheOneAlreadyStored() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(3L);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(bookId)).thenReturn(Optional.of(book));
        service.release(bookId, holderId);
    }

    @Test
    public void release_ReturnsBook_WhenBookIsNotAlreadyBeingHold() {
        long bookId = 1L;
        long holderId = 1L;
        book.setHolderId(holderId);
        Mockito.when(userRepository.get(holderId)).thenReturn(Optional.of(user));
        Mockito.when(repository.get(bookId)).thenReturn(Optional.of(book));
        book.setHolderId(null);
        Mockito.when(repository.release(bookId)).thenReturn(Optional.of(book));
        Book heldBook = service.release(bookId, holderId);

        assertNull(heldBook.getHolderId());
    }
}
