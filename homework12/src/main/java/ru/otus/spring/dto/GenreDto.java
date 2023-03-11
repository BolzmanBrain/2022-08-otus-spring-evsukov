package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.Genre;

@Data
public class GenreDto {
    private final Long id;
    private final String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
