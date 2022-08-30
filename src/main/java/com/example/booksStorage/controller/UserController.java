package com.example.booksStorage.controller;

import com.example.booksStorage.domain.User;
import com.example.booksStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
         return ResponseEntity.ok(service.get(userId));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user
    ) {
        return ResponseEntity.ok(service.update(userId, user));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.delete(userId));
    }
}
