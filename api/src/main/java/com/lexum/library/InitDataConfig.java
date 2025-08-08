package com.lexum.library;

import com.lexum.library.entity.Book;
import com.lexum.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("!test") // désactive cette config si profil "test" actif
public class InitDataConfig {

    @Bean
    public CommandLineRunner init(BookRepository repo) {
        return args -> {
            repo.save(new Book(null, "Le Petit Prince", List.of("Antoine de Saint-Exupéry"), LocalDate.of(1943, 4, 6), "Un conte poétique.", 96));
            repo.save(new Book(null, "Effective Java", List.of("Joshua Bloch"), LocalDate.of(2018, 1, 6), "Bonnes pratiques Java.", 416));
            repo.save(new Book(null, "Clean Code", List.of("Robert C. Martin"), LocalDate.of(2008, 8, 1), "Guide du code propre.", 464));
        };
    }
}
