package com.example.booksStorage.controller;

import com.example.booksStorage.converter.LetterConverter;
import com.example.booksStorage.dto.HolderDto;
import com.example.booksStorage.dto.LetterDto;
import com.example.booksStorage.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/letters")
public class LetterController {
    @Autowired
    private LetterService service;
    @Autowired
    private LetterConverter converter;

    @GetMapping
    public ResponseEntity<List<LetterDto>> getAll() {
        return ResponseEntity.ok(
                converter.entityListToDtoList(service.getAll()
                )
        );
    }

    @GetMapping("{letterId}")
    public ResponseEntity<?> get(@PathVariable("letterId") Long letterId) {
        return ResponseEntity.ok(
                converter.entityToDto(
                        service.get(letterId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<LetterDto> add(@RequestBody LetterDto letter) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.add(converter.dtoToEntity(letter))
                )
        );
    }

    @PutMapping("{letterId}")
    public ResponseEntity<?> update(
            @PathVariable("letterId") Long letterId,
            @RequestBody LetterDto letter
    ) {
        return ResponseEntity.ok(
                converter.entityToDto(
                        service.update(letterId, converter.dtoToEntity(letter))
                )
        );
    }

    @DeleteMapping("{letterId}")
    public ResponseEntity<?> delete(@PathVariable("letterId") Long letterId) {
        service.delete(letterId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("{letterId}/hold")
    public ResponseEntity<LetterDto> holdLetter(@PathVariable("letterId") Long letterId, @RequestBody HolderDto user) {
        LetterDto letter = converter.entityToDto(
                service.hold(letterId, user.getHolderId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(letter);
    }

    @PutMapping("{letterId}/release")
    public ResponseEntity<LetterDto> releaseLetter(@PathVariable("letterId") Long letterId, @RequestBody HolderDto user) {
        LetterDto letter = converter.entityToDto(
                service.release(letterId, user.getHolderId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(letter);
    }
}
