package com.example.booksStorage.controller;

import com.example.booksStorage.converter.MagazineConverter;
import com.example.booksStorage.domain.Holder;
import com.example.booksStorage.dto.MagazineDto;
import com.example.booksStorage.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/magazines")
public class MagazineController {
    @Autowired
    private MagazineService service;
    @Autowired
    private MagazineConverter converter;


    @GetMapping
    public ResponseEntity<List<MagazineDto>> getAll() {
        return ResponseEntity.ok(
                converter.entityListToDtoList(service.getAll())
        );
    }

    @GetMapping("{magazineId}")
    public ResponseEntity<?> get(@PathVariable("magazineId") Long magazineId) {
        return ResponseEntity.ok(
                converter.entityToDto(service.get(magazineId))
        );
    }

    @PostMapping
    public ResponseEntity<MagazineDto> add(@RequestBody MagazineDto magazine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                converter.entityToDto(
                        service.add(converter.dtoToEntity(magazine))
                )
        );
    }

    @PutMapping("{magazineId}")
    public ResponseEntity<?> update(
            @PathVariable("magazineId") Long magazineId,
            @RequestBody MagazineDto magazine
    ) {
        return ResponseEntity.ok(
                converter.entityToDto(
                        service.update(magazineId, converter.dtoToEntity(magazine))
                )
        );
    }

    @DeleteMapping("{magazineId}")
    public ResponseEntity<?> delete(@PathVariable("magazineId") Long magazineId) {
        service.delete(magazineId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{magazineId}/hold")
    public ResponseEntity<MagazineDto> holdMagazineDto(@PathVariable("magazineId") Long magazineId, @RequestBody Holder user) {
        MagazineDto magazine = converter.entityToDto(service.hold(magazineId, user.getHolderId()));
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }

    @PutMapping("{magazineId}/release")
    public ResponseEntity<MagazineDto> releaseMagazineDto(@PathVariable("magazineId") Long magazineId, @RequestBody Holder user) {
        MagazineDto magazine = converter.entityToDto(service.release(magazineId, user.getHolderId()));
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }
}
