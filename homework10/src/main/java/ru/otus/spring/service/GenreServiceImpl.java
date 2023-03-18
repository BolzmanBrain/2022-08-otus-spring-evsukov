package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.ConstraintViolatedException;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public long count() {
        return genreRepository.count();
    }

    @Override
    public Genre save(Genre genre) {
        try {
            return genreRepository.save(genre);
        }
        catch (RuntimeException e) {
            throw new ConstraintViolatedException("Unique constraint violated",e);
        }
    }

    @Override
    public void deleteById(long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            try {
                genreRepository.delete(genre);
            } catch (RuntimeException e) {
                throw new ConstraintViolatedException("Foreign key violated",e);
            }
        }
    }
}
