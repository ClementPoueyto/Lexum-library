package com.lexum.library.mapper;
import com.lexum.library.dto.BookDto;
import com.lexum.library.dto.CreateBookRequest;
import com.lexum.library.entity.Book;

public class BookMapper {

    public static BookDto toDto(Book b) {
        if (b == null) return null;
        BookDto dto = new BookDto();
        dto.setId(b.getId());
        dto.setTitle(b.getTitle());
        dto.setAuthors(b.getAuthors());
        dto.setPublicationDate(b.getPublicationDate());
        dto.setSummary(b.getSummary());
        dto.setPages(b.getPages());
        return dto;
    }

    public static Book fromCreate(CreateBookRequest r) {
        Book b = new Book();
        b.setTitle(r.getTitle());
        b.setAuthors(r.getAuthors());
        b.setPublicationDate(r.getPublicationDate());
        b.setSummary(r.getSummary());
        b.setPages(r.getPages());
        return b;
    }
}