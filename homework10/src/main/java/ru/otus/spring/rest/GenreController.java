package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.exceptions.dtos.GenreDto;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/v1/genres")
    public List<GenreDto> getAuthors() {
        return genreService.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }
}
