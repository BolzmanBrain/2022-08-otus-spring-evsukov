package ru.otus.spring.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.LibraryApplication;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookCommentDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = { LibraryApplication.class, SecurityConfiguration.class })
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;

    private static final String USERNAME = "user";
    private static final String USER_ROLE = "user";

    @WithAnonymousUser
    @Test
    void shouldGetBooks_Anonymous() throws Exception {
        val books = List.of(getTestBook());
        given(bookService.findAll()).willReturn(books);

        val expected = books.stream()
                .map(BookDto::toDto)
                .map(this::convertToJson)
                .collect(Collectors.toList()).toString();
        val actual = mvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldGetBookById_Authenticated() throws Exception {
        val ID_BOOK = 1L;
        val book = getTestBook();
        val dto = BookDto.toDto(book);
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        val expected = convertToJson(dto);
        val actual = mvc.perform(get("/api/v1/books/" + ID_BOOK))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @WithAnonymousUser
    @Test
    void shouldGetBookById_Anonymous() throws Exception {
        val ID_BOOK = 1L;

        mvc.perform(get("/api/v1/books/" + ID_BOOK))
                .andExpect(status().isFound());
    }

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldDeleteBookById_Authenticated() throws Exception {
        val ID_BOOK = 1L;
        mvc.perform(delete("/api/v1/books/" + ID_BOOK))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(ID_BOOK);
    }

    @WithAnonymousUser
    @Test
    void shouldDeleteBookById_Anonymous() throws Exception {
        val ID_BOOK = 1L;
        mvc.perform(delete("/api/v1/books/" + ID_BOOK))
                .andExpect(status().isFound());
    }

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldCreateBook_Authenticated() throws Exception {
        val book = getTestBook();
        val dto = BookDto.toDto(book);

        mvc.perform(post("/api/v1/books")
                        .content(convertToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).save(dto);
    }

    @WithAnonymousUser
    @Test
    void shouldCreateBook_Anonymous() throws Exception {
        val book = getTestBook();
        val dto = BookDto.toDto(book);

        mvc.perform(post("/api/v1/books")
                        .content(convertToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldUpdateBook_Authenticated() throws Exception {
        val book = getTestBook();
        val dto = BookDto.toDto(book);

        mvc.perform(put("/api/v1/books")
                        .content(convertToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).save(dto);
    }

    @WithAnonymousUser
    @Test
    void shouldUpdateBook_Anonymous() throws Exception {
        val book = getTestBook();
        val dto = BookDto.toDto(book);

        mvc.perform(put("/api/v1/books")
                        .content(convertToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldGetComments_Authenticated() throws Exception {
        val book = getTestBook();
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        val expectedList = book.getComments().stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList());
        val expected = convertToJson(expectedList);
        val actual = mvc.perform(get(String.format("/api/v1/books/%d/comments", book.getId())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @WithAnonymousUser
    @Test
    void shouldGetComments_Anonymous() throws Exception {
        val book = getTestBook();

        mvc.perform(get(String.format("/api/v1/books/%d/comments", book.getId())))
                .andExpect(status().isFound());
    }

    private Book getTestBook() {
        Author author = new Author(1L,"1");
        Genre genre = new Genre(1L, "1");

        Book book = new Book(1L, "book", author, genre);
        List<BookComment> bookComments = List.of(new BookComment(1L, "text", book));
        book.setComments(bookComments);
        return book;
    }

    private String convertToJson(Object object)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}