package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exception.ConstraintViolatedException;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(long id) {
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
            return authorRepository.save(author);
        }
        catch (RuntimeException e) {
            throw new ConstraintViolatedException("Unique constraint violated",e);
        }
    }

    @Override
    public void deleteById(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            try {
                authorRepository.delete(author);
            } catch (RuntimeException e) {
                throw new ConstraintViolatedException("Foreign key violated",e);
            }
        }
    }
}
