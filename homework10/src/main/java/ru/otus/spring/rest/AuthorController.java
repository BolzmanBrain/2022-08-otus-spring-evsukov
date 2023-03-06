package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.exceptions.dtos.AuthorDto;
import ru.otus.spring.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/v1/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }
}
