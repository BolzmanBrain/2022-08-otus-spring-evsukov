package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.services.AuthorService;
import ru.otus.spring.services.BookService;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {
    public final static String BOOKS_VIEW = "books";
    public final static String EDIT_BOOK_VIEW = "edit_book";
    public final static String ADD_BOOK_VIEW = "add_book";
    public final static String OBJECT_NOT_FOUND = "Object not found";
    private final static String REDIRECT = "redirect:/";
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping(value = {"/","/books"})
    public String booksPage(Model model) {
        List<BookDto> dtos = bookService.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", dtos);
        return BOOKS_VIEW;
    }

    @GetMapping("/edit_book")
    public String editBookPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(NotFoundException::new);
        BookDto bookDto = BookDto.toDto(book);
        List<BookCommentDto> commentDtos = book.getComments().stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList());

        model.addAttribute("book", bookDto);
        model.addAttribute("comments", commentDtos);
        addAuthorsAndGenresToModel(model);
        return EDIT_BOOK_VIEW;
    }

    @PostMapping("/edit_book")
    public String editBook(@RequestParam("id") long id,
                           @RequestParam("name") String name,
                           @RequestParam("id_author") long idAuthor,
                           @RequestParam("id_genre") long idGenre) {
        BookDto bookDto = BookDto.createForUpdate(id, name, idAuthor, idGenre);
        bookService.save(bookDto);
        return REDIRECT;
    }

    @GetMapping("/add_book")
    public String addBookPage(Model model) {
        // плохая идея для заполнения дефолтных значений в шаблоне Thymeleaf
        BookDto defaultValues = BookDto.createForInsert("",0L,0L);
        model.addAttribute("book", defaultValues);
        addAuthorsAndGenresToModel(model);
        return ADD_BOOK_VIEW;
    }

    @PostMapping("/add_book")
    public String addBook(@RequestParam("name") String name,
                          @RequestParam("id_author") long idAuthor,
                          @RequestParam("id_genre") long idGenre) {
        BookDto bookDto = BookDto.createForInsert(name, idAuthor, idGenre);
        bookService.save(bookDto);
        return REDIRECT;
    }

    @PostMapping("/delete_book")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return REDIRECT;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body(OBJECT_NOT_FOUND);
    }


    private void addAuthorsAndGenresToModel(Model model) {
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
    }
}
