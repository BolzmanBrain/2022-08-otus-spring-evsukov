package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.Author;

@Data
public class AuthorDto {
    private final Long id;
    private final String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
