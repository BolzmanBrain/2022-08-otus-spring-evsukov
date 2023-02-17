package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.services.AuthorService;
import ru.otus.spring.services.BookService;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    private final static String BOOK_NAME = "book";
    private final static String COMMENT_TEXT = "comment";

    @Test
    void shouldGetRoot_Correct() throws Exception {
        String MODEL_ATTRIBUTE_KEY = "books";
        List<Book> books = List.of(getTestBook());
        given(bookService.findAll()).willReturn(books);

        List<BookDto> expectedBookDtos = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        val modelAndView = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView();
        val viewName = modelAndView.getViewName();
        val booksFromModel = modelAndView.getModelMap().get(MODEL_ATTRIBUTE_KEY);

        assertAll(
                () -> assertEquals(BookController.BOOKS_VIEW, viewName),
                () -> assertEquals(expectedBookDtos, booksFromModel)
        );
    }

    @Test
    void shouldGetEditBook_Correct() throws Exception {
        String MODEL_ATTRIBUTE_KEY_BOOK = "book";
        String MODEL_ATTRIBUTE_KEY_COMMENTS = "comments";
        Book book = getTestBook();
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        BookDto expectedBookDto = BookDto.toDto(book);
        List<BookCommentDto> expectedCommentDtos = book.getComments().stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList());

        val modelAndView = mvc.perform(get("/edit_book?id=" + book.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView();
        val viewName = modelAndView.getViewName();
        val bookFromModel = modelAndView.getModelMap().get(MODEL_ATTRIBUTE_KEY_BOOK);
        val commentsFromModel = modelAndView.getModelMap().get(MODEL_ATTRIBUTE_KEY_COMMENTS);

        assertAll(
                () -> assertEquals(BookController.EDIT_BOOK_VIEW, viewName),
                () -> assertEquals(expectedBookDto, bookFromModel),
                () -> assertEquals(expectedCommentDtos, commentsFromModel)
        );
    }

    @Test
    void shouldGetEditBook_HandleWrongBookId() throws Exception {
        given(bookService.findById(anyLong())).willReturn(Optional.empty());

        mvc.perform(get("/edit_book?id=100500"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(BookController.OBJECT_NOT_FOUND));
    }

    @Test
    void shouldPostEditBook_Correct() throws Exception {
        Book book = getTestBook();
        BookDto bookDto = BookDto.createForUpdate(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/edit_book")
                .param("id", bookDto.getId().toString())
                .param("name", bookDto.getName())
                .param("id_author", bookDto.getIdAuthor().toString())
                .param("id_genre", bookDto.getIdGenre().toString()))
                .andExpect(status().isFound());
        verify(bookService, times(1)).save(bookDto);
    }

    @Test
    void shouldGetAddBook_Correct() throws Exception {
        val modelAndView = mvc.perform(get("/add_book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView();

        val viewName = modelAndView.getViewName();
        assertEquals(BookController.ADD_BOOK_VIEW, viewName);
    }

    @Test
    void shouldPostAddBook_Correct() throws Exception {
        Book book = getTestBook();
        BookDto bookDto = BookDto.createForInsert(book.getName(), book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/add_book")
                        .param("name", bookDto.getName())
                        .param("id_author", bookDto.getIdAuthor().toString())
                        .param("id_genre", bookDto.getIdGenre().toString()))
                .andExpect(status().isFound());
        verify(bookService, times(1)).save(bookDto);
    }

    @Test
    void shouldPostDeleteBook_Correct() throws Exception {
        Long DELETE_ID = 1L;

        mvc.perform(post("/delete_book")
                        .param("id", DELETE_ID.toString()));
        verify(bookService, times(1)).deleteById(DELETE_ID);
    }

    private Book getTestBook() {
        Author author = new Author(1L, "author");
        Genre genre = new Genre(1L, "genre");
        Book book = new Book(1L, BOOK_NAME, author, genre);
        List<BookComment> comments = List.of(new BookComment(1L, COMMENT_TEXT, book));
        book.setComments(comments);
        return book;
    }
}
