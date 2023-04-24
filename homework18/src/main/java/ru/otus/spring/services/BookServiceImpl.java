package ru.otus.spring.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    // @Transactional is needed for initialization of lazy fields
    @HystrixCommand(commandKey="findByIdBook", fallbackMethod="buildFallbackFindById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            })
    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            initializeBookComments(book);
        }
        return optionalBook;
    }

    @HystrixCommand(commandKey="findAllBook", fallbackMethod="buildFallbackFindAll",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            })
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @HystrixCommand(commandKey="countBook", fallbackMethod="buildFallbackCount",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            })
    @Override
    public long count() {
        return bookRepository.count();
    }

    @HystrixCommand(commandKey="saveBook", fallbackMethod="buildFallbackSave",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            })
    @Override
    public Book save(BookDto bookDto) {
        try {
            Author author = authorRepository.findById(bookDto.getIdAuthor()).orElseThrow();
            Genre genre = genreRepository.findById(bookDto.getIdGenre()).orElseThrow();
            Book book;

            if(bookDto.getId() == null) {
                book = Book.createForInsert(bookDto.getName(), author, genre);
            } else {
                // create for update
                book = new Book(bookDto.getId(), bookDto.getName(), author, genre);
            }
            return bookRepository.save(book);
        }
        catch (NoSuchElementException e) {
            throw new ConstraintViolatedException("Provided wrong id",e);
        }
    }

    // It is not necessary to use @Transactional in most DML methods if you use
    // Spring Data, because many of its methods are already wrapped in @Transactional.
    // However, I have to make this method @Transactional, due to orphanRemove = true.
    @HystrixCommand(commandKey="deleteByIdBook", fallbackMethod="buildFallbackDeleteById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            })
    @Transactional
    @Override
    public void deleteById(long id) {
        Optional<Book> optionalBook = findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
        }
    }

    public Optional<Book> buildFallbackFindById(long id) {
        return Optional.empty();
    }

    public List<Book> buildFallbackFindAll() {
        return new ArrayList<>();
    }

    public long buildFallbackCount() {
        return 0;
    }

    public Book buildFallbackSave(Book book) {
        return null;
    }

    public void buildFallbackDeleteById(long id) {
    }

    private void initializeBookComments(Book book) {
        // manual initialization of lazy field by accessing it
        List<BookComment> bookCommentsComments = book.getComments();
        Object[] array = bookCommentsComments.toArray();
    }
}
