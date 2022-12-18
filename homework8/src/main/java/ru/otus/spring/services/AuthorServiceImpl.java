package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

    @Override
    public Author save(Author author) {
        try {
            return authorRepository.saveEnsureConsistency(author);
        }
        catch (RuntimeException e) {
            throw new ConsistencyViolatedException("Uniqueness violated",e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            authorRepository.deleteEnsureConsistencyById(id);
        } catch (IllegalArgumentException e) {
            // id of a wrong format is specified. No action is required
            return;
        }
        catch (RuntimeException e) {
            throw new ConsistencyViolatedException("Reference integrity violated",e);
        }
    }
}
