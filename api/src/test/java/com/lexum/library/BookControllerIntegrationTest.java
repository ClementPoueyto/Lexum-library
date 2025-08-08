package com.lexum.library;

import com.lexum.library.entity.Book;
import com.lexum.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository; // Could be improved by adding embedded database for tests

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

        Book book = new Book();
        book.setTitle("Test Driven Development");
        book.setAuthors(List.of("Kent Beck"));
        book.setPublicationDate(LocalDate.of(2003, 1, 1));
        book.setSummary("TDD explained.");
        book.setPages(240);

        bookRepository.save(book);
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/api/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title", is("Test Driven Development")));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        Long id = bookRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Driven Development")))
                .andExpect(jsonPath("$.authors[0]", is("Kent Beck")));
    }

    @Test
    void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/books/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")));
    }
}
