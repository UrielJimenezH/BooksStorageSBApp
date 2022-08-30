package com.example.booksStorage.service;

import com.example.booksStorage.domain.User;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
        return repository.getAll();
    }

    public User get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("User with id " + id + " does not exist"));
    }

    public User add(User user) {
        User newUser = repository.save(user);
        return newUser;
    }

    public User update(Long id, User newUser) {
        newUser.setId(id);
        Optional<User> updatedUser = repository.update(newUser);
        return updatedUser
                .orElseThrow(() -> new NoSuchElementFoundException("User with id " + id + " does not exist"));
    }

    public User delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("User with id " + id + " does not exist"));
    }
}
