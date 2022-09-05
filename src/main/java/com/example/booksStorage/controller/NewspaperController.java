package com.example.booksStorage.controller;

import com.example.booksStorage.converter.NewspaperConverter;
import com.example.booksStorage.domain.Holder;
import com.example.booksStorage.dto.NewspaperDto;
import com.example.booksStorage.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/newspapers")
public class NewspaperController {
    @Autowired
    private NewspaperService service;
    @Autowired
    private NewspaperConverter converter;

    @GetMapping
    public ResponseEntity<List<NewspaperDto>> getAll() {
        return ResponseEntity.ok(
                converter.entityListToDtoList(service.getAll())
        );
    }

    @GetMapping("{newspaperId}")
    public ResponseEntity<?> get(@PathVariable("newspaperId") Long newspaperId) {
        return ResponseEntity.ok(
                converter.entityToDto(service.get(newspaperId))
        );
    }

    @PostMapping
    public ResponseEntity<NewspaperDto> add(@RequestBody NewspaperDto newspaper) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.add(converter.dtoToEntity(newspaper))
                )
        );
    }

    @PutMapping("{newspaperId}")
    public ResponseEntity<?> update(
            @PathVariable("newspaperId") Long newspaperId,
            @RequestBody NewspaperDto newspaper
    ) {
        return ResponseEntity.ok(
                converter.entityToDto(
                        service.update(newspaperId, converter.dtoToEntity(newspaper))
                )
        );
    }

    @DeleteMapping("{newspaperId}")
    public ResponseEntity<?> delete(@PathVariable("newspaperId") Long newspaperId) {
        service.delete(newspaperId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{newspaperId}/hold")
    public ResponseEntity<NewspaperDto> holdNewspaper(@PathVariable("newspaperId") Long newspaperId, @RequestBody Holder user) {
        NewspaperDto newspaper = converter.entityToDto(service.hold(newspaperId, user.getHolderId()));
        return ResponseEntity.status(HttpStatus.OK).body(newspaper);
    }

    @PutMapping("{newspaperId}/release")
    public ResponseEntity<NewspaperDto> releaseNewspaper(@PathVariable("newspaperId") Long newspaperId, @RequestBody Holder user) {
        NewspaperDto newspaper = converter.entityToDto(service.release(newspaperId, user.getHolderId()));
        return ResponseEntity.status(HttpStatus.OK).body(newspaper);
    }
}
