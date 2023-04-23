package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/books")
    public Flux<BookDto> getBooks() {
        return bookRepository.findAll()
                .map(BookDto::toDto);
    }

    @GetMapping("/api/v1/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(BookDto::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/v1/books/{id}")
    public Mono<Void> deleteBookById(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/api/v1/books")
    public Mono<BookDto> createBook(@RequestBody BookDto bookDto) {
        return dtoToBook(bookDto)
                .flatMap(bookRepository::save)
                .map(BookDto::toDto);
    }

    @PutMapping("/api/v1/books")
    public Mono<Void> updateBook(@RequestBody BookDto bookDto) {
        return dtoToBook(bookDto)
                .flatMap(bookRepository::save)
                .then();
    }

    @GetMapping("/api/v1/books/{id}/comments")
    public Flux<BookCommentDto> getBookComments(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(Book::getComments)
                .flatMapMany(Flux::fromIterable)
                .map(BookCommentDto::toDto);
    }

    private Mono<Book> dtoToBook(BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findById(bookDto.getIdAuthor());
        Mono<Genre> genreMono = genreRepository.findById(bookDto.getIdGenre());
        Mono<List<BookComment>> bookCommentsMono;

        if(bookDto.getId() == null) {
            bookCommentsMono = Mono.just(List.of());
        } else {
            bookCommentsMono= bookRepository
                    .findById(bookDto.getId())
                    .map(Book::getComments);
        }
        return Mono.zip(authorMono, genreMono, bookCommentsMono)
                .map(zipped -> new Book(bookDto.getId(), bookDto.getName(), zipped.getT1(), zipped.getT2(),
                        zipped.getT3()));
    }
}
