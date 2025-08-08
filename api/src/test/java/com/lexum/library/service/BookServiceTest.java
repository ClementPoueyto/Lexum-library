package com.lexum.library.service;

import com.lexum.library.dto.BookDto;
import com.lexum.library.entity.Book;
import com.lexum.library.exception.BookNotFoundException;
import com.lexum.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto dto;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Clean Code", List.of("Robert C. Martin"),
                LocalDate.of(2008, 8, 1), "Guide du code propre.", 464);

        dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Clean Code");
        dto.setAuthors(List.of("Robert C. Martin"));
        dto.setPublicationDate(LocalDate.of(2008, 8, 1));
        dto.setSummary("Guide du code propre.");
        dto.setPages(464);
    }

    @Test
    void shouldReturnAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<BookDto> result = bookService.listAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());
    }

    @Test
    void shouldReturnOneBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto result = bookService.getBydId(1L);

        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void shouldThrowWhenBookNotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBydId(999L));
    }

}
