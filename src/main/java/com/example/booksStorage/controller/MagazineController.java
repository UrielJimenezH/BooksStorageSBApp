package com.example.booksStorage.controller;

import com.example.booksStorage.domain.Magazine;
import com.example.booksStorage.domain.Holder;
import com.example.booksStorage.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/magazines")
public class MagazineController {
    private final MagazineService service;

    @Autowired
    public MagazineController(MagazineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Magazine>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{magazineId}")
    public ResponseEntity<?> get(@PathVariable("magazineId") Long magazineId) {
        return ResponseEntity.ok(service.get(magazineId));
    }

    @PostMapping
    public ResponseEntity<Magazine> add(@RequestBody Magazine magazine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(magazine));
    }

    @PutMapping("{magazineId}")
    public ResponseEntity<?> update(
            @PathVariable("magazineId") Long magazineId,
            @RequestBody Magazine magazine
    ) {
        return ResponseEntity.ok(service.update(magazineId, magazine));
    }

    @DeleteMapping("{magazineId}")
    public ResponseEntity<?> delete(@PathVariable("magazineId") Long magazineId) {
        service.delete(magazineId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{magazineId}/hold")
    public ResponseEntity<Magazine> holdMagazine(@PathVariable("magazineId") Long magazineId, @RequestBody Holder user) {
        Magazine magazine = service.hold(magazineId, user.getHolderId());
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }

    @PutMapping("{magazineId}/release")
    public ResponseEntity<Magazine> releaseMagazine(@PathVariable("magazineId") Long magazineId, @RequestBody Holder user) {
        Magazine magazine = service.release(magazineId, user.getHolderId());
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }
}
