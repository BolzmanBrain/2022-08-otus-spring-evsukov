package ru.otus.spring.services;

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

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    // @Transactional is needed for initialization of lazy fields
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

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Transactional
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

    @Transactional
    @Override
    public void deleteById(long id) {
        Optional<Book> optionalBook = findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
        }
    }

    private void initializeBookComments(Book book) {
        // manual initialization of lazy field by accessing it
        List<BookComment> bookCommentsComments = book.getComments();
        Object[] array = bookCommentsComments.toArray();
    }
}
