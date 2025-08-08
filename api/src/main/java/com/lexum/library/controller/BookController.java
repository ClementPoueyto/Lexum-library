package com.lexum.library.controller;

import com.lexum.library.dto.BookDto;
import com.lexum.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Liste paginée des livres
    @GetMapping
    public Page<BookDto> pagination(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return service.listAll(pageable);
    }

    // Détail d’un livre par ID
    @GetMapping("/{id}")
    public BookDto getBydId(@PathVariable Long id) {
        return service.getBydId(id);
    }
}
