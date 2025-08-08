package com.lexum.library.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateBookRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotEmpty(message = "Au moins un auteur est requis")
    private List<@NotBlank(message = "Nom d'auteur vide") String> authors;

    @PastOrPresent(message = "La date de publication ne peut être dans le futur")
    private LocalDate publicationDate;

    @Size(max = 5000, message = "Résumé trop long")
    private String summary;

    @NotNull(message = "Le nombre de pages est requis")
    @Min(value = 1, message = "Le livre doit avoir au moins 1 page")
    private Integer pages;
}