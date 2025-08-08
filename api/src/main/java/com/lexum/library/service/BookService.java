package com.lexum.library.service;

import com.lexum.library.dto.BookDto;
import com.lexum.library.entity.Book;
import com.lexum.library.exception.BookNotFoundException;
import com.lexum.library.mapper.BookMapper;
import com.lexum.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Page<BookDto> listAll(Pageable pageable) {
        Page<Book> bookPage = repo.findAll(pageable);
        return bookPage.map(BookMapper::toDto);
    }

    public BookDto getBydId(Long id) {
        return repo.findById(id).map(BookMapper::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }
}