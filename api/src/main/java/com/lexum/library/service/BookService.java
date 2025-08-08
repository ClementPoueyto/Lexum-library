package com.lexum.library.service;

import com.lexum.library.dto.BookDto;
import com.lexum.library.dto.CreateBookRequest;
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

    private final BookRepository bookRepository;

    public BookService(BookRepository repo) {
        this.bookRepository = repo;
    }

    public Page<BookDto> listAll(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(BookMapper::toDto);
    }

    public BookDto getBydId(Long id) {
        return bookRepository.findById(id).map(BookMapper::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDto create(CreateBookRequest createBookRequest) {
        Book book = BookMapper.fromCreate(createBookRequest);
        Book saved = bookRepository.save(book);
        return  BookMapper.toDto(saved);
    }

    public BookDto update(Long id, CreateBookRequest createBookRequest) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        Book book = BookMapper.fromCreate(createBookRequest);
        book.setId(id); // s'assurer qu'on met à jour l'existant
        Book updated = bookRepository.save(book);
        return BookMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public Page<BookDto> search(String title, String author, Pageable pageable) {
        return bookRepository.search(title, author, pageable)
                .map(BookMapper::toDto);
    }
}