package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.exceptions.dtos.GenreDto;
import ru.otus.spring.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/genres")
    public Flux<GenreDto> getGenres() {
        return genreRepository.findAll()
                .map(GenreDto::toDto);
    }
}
