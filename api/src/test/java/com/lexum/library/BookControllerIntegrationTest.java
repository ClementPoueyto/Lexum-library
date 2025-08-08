package com.lexum.library;

import com.lexum.library.entity.Book;
import com.lexum.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository; // Peut être amélioré en ajoutant une base de donnée embarquée aux tests

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

    @Test
    void shouldCreateBook() throws Exception {
        String body = """
        {
            "title": "New book",
            "authors": ["Someone"],
            "publicationDate": "2023-01-01",
            "summary": "A new book",
            "pages": 123
        }
        """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New book"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Book saved = bookRepository.save(new Book(null, "Book To Delete", List.of("Author"),
                LocalDate.of(2020, 1, 1), "Summary", 200));

        mockMvc.perform(delete("/api/books/" + saved.getId()))
                .andExpect(status().isNoContent());
    }
}
