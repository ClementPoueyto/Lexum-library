package com.lexum.library.controller;

import com.lexum.library.dto.BookDto;
import com.lexum.library.dto.CreateBookRequest;
import com.lexum.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Peut être amélioré en utilisant Swagger pour générer documentation/API
@RestController
@RequestMapping("/api/books")
@CrossOrigin()
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Liste paginée des livres
    @GetMapping
    public Page<BookDto> pagination(@PageableDefault(size = 20, page = 0) Pageable pageable,
                                    @RequestParam(required = false) String query) {
        return service.search(query, pageable);
    }

    // Détail d’un livre par ID
    @GetMapping("/{id}")
    public BookDto getBydId(@PathVariable Long id) {
        return service.getBydId(id);
    }


    // Création d’un livre
    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody CreateBookRequest request) {
        BookDto created = service.create(request);
        return ResponseEntity.status(201).body(created);
    }

    // Modification d’un livre existant
    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @Valid @RequestBody CreateBookRequest request) {
        return service.update(id, request);
    }

    // Suppression d’un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
