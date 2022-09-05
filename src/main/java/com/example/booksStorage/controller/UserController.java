package com.example.booksStorage.controller;

import com.example.booksStorage.converter.UserConverter;
import com.example.booksStorage.dto.UserDto;
import com.example.booksStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserConverter converter;

    @GetMapping
        public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(
                converter.entityListToDtoList(service.getAll())
        );
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
         return ResponseEntity.ok(
                 converter.entityToDto(service.get(userId))
         );
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.add(converter.dtoToEntity(user))
                )
        );
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserDto user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.update(userId, converter.dtoToEntity(user))
                )
        );
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        service.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
