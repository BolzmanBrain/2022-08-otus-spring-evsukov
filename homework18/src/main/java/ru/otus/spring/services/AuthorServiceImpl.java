package ru.otus.spring.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @HystrixCommand(commandKey="findByIdAuthor", fallbackMethod="buildFallbackFindById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @HystrixCommand(commandKey="findAllAuthor", fallbackMethod="buildFallbackFindAll",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @HystrixCommand(commandKey="countAuthor", fallbackMethod="buildFallbackCount",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public long count() {
        return authorRepository.count();
    }

    @HystrixCommand(commandKey="saveAuthor", fallbackMethod="buildFallbackSave",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public Author save(Author author) {
        try {
            return authorRepository.save(author);
        }
        catch (RuntimeException e) {
            throw new ConstraintViolatedException("Unique constraint violated",e);
        }
    }

    @HystrixCommand(commandKey="deleteByIdAuthor", fallbackMethod="buildFallbackDeleteById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
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

    public Optional<Author> buildFallbackFindById(long id) {
        return Optional.empty();
    }

    public List<Author> buildFallbackFindAll() {
        return new ArrayList<>();
    }

    public long buildFallbackCount() {
        return 0;
    }

    public Author buildFallbackSave(Author author) {
        return null;
    }

    public void buildFallbackDeleteById(long id) {}
}
