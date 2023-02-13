package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> findById(String id) {
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
            return genreRepository.saveEnsureConsistency(genre);
        }
        catch (RuntimeException e) {
            throw new ConsistencyViolatedException("Uniqueness violated",e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            genreRepository.deleteEnsureConsistencyById(id);
        } catch (IllegalArgumentException e) {
            // id of a wrong format is specified. No action is required
            return;
        }
        catch (RuntimeException e) {
            throw new ConsistencyViolatedException("Reference integrity violated",e);
        }
    }
}
