package com.example.booksStorage;

import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.user.User;
import com.example.booksStorage.user.UserRepository;
import com.example.booksStorage.user.UserService;
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
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;
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
        Mockito.when(repository.getAll()).thenReturn(List.of(user));

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenUserWasNotFound() {
        long id = 1;
        Mockito.when(repository.get(id)).thenThrow(new NoSuchElementFoundException("User with id " + id + " does not exist"));

        service.get(id);
    }

    @Test
    public void get_ReturnsUser_WhenUserExists() {
        long id = 1;
        Mockito.when(repository.get(id)).thenReturn(Optional.of(user));

        assertEquals(user, service.get(id));
    }

    @Test
    public void add_ReturnsUser() {
        Mockito.when(repository.save(user)).thenReturn(user);

        assertNotNull(service.add(user));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenUserWasNotFound() {
        Mockito.when(repository.update(user)).thenThrow(new NoSuchElementFoundException("User with id 1 does not exist"));

        service.update(user.getId(), user);
    }

    @Test
    public void update_ReturnsUser_WhenUserExists() {
        Mockito.when(repository.update(user)).thenReturn(Optional.of(user));

        assertEquals(user, service.update(user.getId(), user));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void delete_ThrowsException_WhenUserWasNotFound() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenThrow(new NoSuchElementFoundException("User with id " + id + " does not exist"));

        service.delete(id);
    }

    @Test
    public void delete_ReturnsUser_WhenUserExists() {
        long id = 1;
        Mockito.when(repository.delete(id)).thenReturn(Optional.of(user));

        assertEquals(user, service.delete(id));
    }
}

