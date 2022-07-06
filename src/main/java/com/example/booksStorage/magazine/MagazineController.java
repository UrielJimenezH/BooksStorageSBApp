package com.example.booksStorage.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
         Optional<Magazine> opMagazine = service.get(magazineId);

         if (opMagazine.isPresent())
             return ResponseEntity.ok(opMagazine.get());

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Magazine with id " + magazineId + " does not exist");
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
        Optional<Magazine> opMagazine = service.update(magazineId, magazine);

        if (opMagazine.isPresent())
            return ResponseEntity.ok(opMagazine.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Magazine with id " + magazineId + " does not exist");
    }

    @DeleteMapping("{magazineId}")
    public ResponseEntity<?> delete(@PathVariable("magazineId") Long magazineId) {
        Optional<Magazine> opMagazine = service.delete(magazineId);

        if (opMagazine.isPresent())
            return ResponseEntity.ok(opMagazine.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Magazine with id " + magazineId + " does not exist");
    }
}
