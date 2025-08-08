package com.lexum.library.controller;

import com.lexum.library.dto.BookDto;
import com.lexum.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Clean Architecture");
        dto.setAuthors(List.of("Robert C. Martin"));
        dto.setPublicationDate(LocalDate.of(2017, 9, 20));
        dto.setSummary("Software design principles");
        dto.setPages(352);

        Page<BookDto> page = new PageImpl<>(List.of(dto));

        when(bookService.listAll(Pageable.ofSize(10))).thenReturn(page);

        mockMvc.perform(get("/api/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("Clean Architecture")));
    }

    @Test
    void shouldReturnOneBookById() throws Exception {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Effective Java");
        dto.setAuthors(List.of("Joshua Bloch"));
        dto.setPublicationDate(LocalDate.of(2018, 1, 6));
        dto.setSummary("Best practices for Java development.");
        dto.setPages(412);

        when(bookService.getBydId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Effective Java")))
                .andExpect(jsonPath("$.authors[0]", is("Joshua Bloch")));
    }
}
