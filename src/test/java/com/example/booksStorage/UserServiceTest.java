package com.example.booksStorage;

import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.service.UserService;
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
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private UserService service;
    @Mock
    private TypedQuery<User> query;
    private final String namedQuery = "query_find_all_users";
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
        Mockito.when(entityManager.createNamedQuery(namedQuery, User.class)).thenReturn(query);

        assertEquals(0, service.getAll().size());
    }

    @Test
    public void getAll_ReturnsList() {
        List<User> users = List.of(user);
        Mockito.when(query.getResultList()).thenReturn(users);
        Mockito.when(entityManager.createNamedQuery(namedQuery, User.class)).thenReturn(query);

        assertEquals(1, service.getAll().size());
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void get_ThrowsException_WhenUserWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(User.class, id)).thenReturn(null);

        service.get(id);
    }

    @Test
    public void get_ReturnsUser_WhenUserExists() {
        long id = 1;
        Mockito.when(entityManager.find(User.class, id)).thenReturn(user);

        assertEquals(user, service.get(id));
    }

    @Test
    public void add_ReturnsUser() {
        assertNotNull(service.add(user));
    }

    @Test(expected = NoSuchElementFoundException.class)
    public void update_ThrowsException_WhenUserWasNotFound() {
        long userId = 10L;
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(null);

        service.update(userId, user);
    }

    @Test
    public void update_ReturnsUser_WhenUserExists() {
        long userId = 10L;
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(user);

        assertEquals(user, service.update(userId, user));
    }

    @Test
    public void delete_DoesNotThrowException_WhenUserWasNotFound() {
        long id = 1;
        Mockito.when(entityManager.find(User.class, id)).thenReturn(null);

        service.delete(id);
    }

    @Test
    public void delete_ReturnsUser_WhenUserExists() {
        long id = 1;
        Mockito.when(entityManager.find(User.class, id)).thenReturn(user);

        service.delete(id);
    }
}

