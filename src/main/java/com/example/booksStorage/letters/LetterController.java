package com.example.booksStorage.letters;

import com.example.booksStorage.user.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/letters")
public class LetterController {
    private final LetterService service;

    @Autowired
    public LetterController(LetterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Letter>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{letterId}")
    public ResponseEntity<?> get(@PathVariable("letterId") Long letterId) {
        return ResponseEntity.ok(service.get(letterId));
    }

    @PostMapping
    public ResponseEntity<Letter> add(@RequestBody Letter letter) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(letter));
    }

    @PutMapping("{letterId}")
    public ResponseEntity<?> update(
            @PathVariable("letterId") Long letterId,
            @RequestBody Letter letter
    ) {
        return ResponseEntity.ok(service.update(letterId, letter));
    }

    @DeleteMapping("{letterId}")
    public ResponseEntity<?> delete(@PathVariable("letterId") Long letterId) {
        return ResponseEntity.ok(service.delete(letterId));
    }
    @PostMapping("{letterId}/hold")
    public ResponseEntity<Letter> holdLetter(@PathVariable("letterId") Long letterId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.hold(letterId, user.getHolderId()));
    }

    @PostMapping("{letterId}/release")
    public ResponseEntity<Letter> releaseLetter(@PathVariable("letterId") Long letterId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.release(letterId, user.getHolderId()));
    }
}
