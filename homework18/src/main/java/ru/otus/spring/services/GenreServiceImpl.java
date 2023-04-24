package ru.otus.spring.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @HystrixCommand(commandKey="findByIdGenre", fallbackMethod="buildFallbackFindById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @HystrixCommand(commandKey="findAllGenre", fallbackMethod="buildFallbackFindAll",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @HystrixCommand(commandKey="countGenre", fallbackMethod="buildFallbackCount",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public long count() {
        return genreRepository.count();
    }

    @HystrixCommand(commandKey="saveGenre", fallbackMethod="buildFallbackSave",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public Genre save(Genre genre) {
        try {
            return genreRepository.save(genre);
        }
        catch (RuntimeException e) {
            throw new ConstraintViolatedException("Unique constraint violated",e);
        }
    }

    @HystrixCommand(commandKey="deleteByIdGenre", fallbackMethod="buildFallbackDeleteById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
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

    public Optional<Genre> buildFallbackFindById(long id) {
        return Optional.empty();
    }

    public List<Genre> buildFallbackFindAll() {
        return new ArrayList<>();
    }

    public long buildFallbackCount() {
        return 0;
    }

    public Genre buildFallbackSave(Genre genre) {
        return null;
    }

    public void buildFallbackDeleteById(long id) {}
}
