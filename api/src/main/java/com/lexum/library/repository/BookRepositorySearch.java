package com.lexum.library.repository;

import com.lexum.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositorySearch {

    Page<Book> search(String title, String author, Pageable pageable);
}
