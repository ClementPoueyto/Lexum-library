package com.lexum.library.repository;

import com.lexum.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(BookRepositorySearchImpl.class)
class BookRepositorySearchImplTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        em.persist(new Book(null, "Clean Code", List.of("Robert C. Martin"),
                LocalDate.of(2008, 8, 1), "Guide de bonnes pratiques", 464));
        em.persist(new Book(null, "Effective Java", List.of("Joshua Bloch"),
                LocalDate.of(2018, 1, 6), "Bonnes pratiques Java", 416));
        em.persist(new Book(null, "Le Petit Prince", List.of("Antoine de Saint-Exupéry"),
                LocalDate.of(1943, 4, 6), "Conte poétique", 96));
        em.flush();
    }

    @Test
    void shouldFindBooksByTitle() {
        Page<Book> result = bookRepository.search("clean", PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());
    }

    @Test
    void shouldFindBooksByAuthor() {
        Page<Book> result = bookRepository.search( "bloch", PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("Effective Java", result.getContent().get(0).getTitle());
    }

    @Test
    void shouldFindBooksByTitleAndAuthor() {
        Page<Book> result = bookRepository.search("i", PageRequest.of(0, 10));
        assertEquals(3, result.getTotalElements());
        assertEquals("Le Petit Prince", result.getContent().get(2).getTitle());
    }

    @Test
    void shouldReturnEmptyWhenNoMatch() {
        Page<Book> result = bookRepository.search("unknown", PageRequest.of(0, 10));
        assertTrue(result.isEmpty());
    }
}
