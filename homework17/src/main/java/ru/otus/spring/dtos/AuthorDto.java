package ru.otus.spring.exceptions.dtos;

import lombok.Data;
import ru.otus.spring.domain.Author;

@Data
public class AuthorDto {
    private final String id;
    private final String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
