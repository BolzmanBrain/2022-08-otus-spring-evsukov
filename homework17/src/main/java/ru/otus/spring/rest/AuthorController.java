package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.exceptions.dtos.AuthorDto;
import ru.otus.spring.repository.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping("/api/v1/authors")
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(AuthorDto::toDto);
    }
}
