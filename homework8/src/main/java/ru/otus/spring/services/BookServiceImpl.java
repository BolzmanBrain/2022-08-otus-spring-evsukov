package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public Book save(BookDto bookDto) {
        try {
            Author author = authorRepository.findById(bookDto.getIdAuthor()).orElseThrow();
            Genre genre = genreRepository.findById(bookDto.getIdGenre()).orElseThrow();
            Book book;

            if(bookDto.getId() == null) {
                book = Book.createForInsert(bookDto.getName(), author, genre);
            } else {
                List<BookComment> bookComments = findById(bookDto.getId()).orElseThrow().getComments();
                book = new Book(bookDto.getId(), bookDto.getName(), author, genre, bookComments);
            }
            return bookRepository.save(book);
        }
        catch (NoSuchElementException e) {
            throw new ConstraintViolatedException("Provided wrong id",e);
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<Book> optionalBook = findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
        }
    }

}
