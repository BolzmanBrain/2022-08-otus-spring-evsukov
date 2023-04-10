package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookCommentDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public List<BookDto> getBooks() {
        return bookService.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getBookById(@PathVariable long id) {
        return bookService.findById(id)
                .map(BookDto::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBookById(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/api/v1/books")
    public void createBook(@RequestBody BookDto bookDto) {
        bookService.save(bookDto);
    }

    @PutMapping("/api/v1/books")
    public void updateBook(@RequestBody BookDto bookDto) {
        bookService.save(bookDto);
    }

    @GetMapping("/api/v1/books/{id}/comments")
    public List<BookCommentDto> getBookComments(@PathVariable long id) {
        Optional<Book> optionalBook = bookService.findById(id);

        return optionalBook.map(book -> book
                .getComments().stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
