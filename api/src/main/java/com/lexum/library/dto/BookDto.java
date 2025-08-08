package com.lexum.library.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private List<String> authors;
    private LocalDate publicationDate;
    private String summary;
    private Integer pages;
}