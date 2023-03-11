package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.LibraryApplication;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@ContextConfiguration(classes = { LibraryApplication.class, SecurityConfiguration.class })
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;

    @WithAnonymousUser
    @Test
    void shouldGetAuthors_Anonymous() throws Exception {
        val authors = getTestAuthors();
        given(authorService.findAll()).willReturn(authors);

        val expected = convertToJson(authors);
        val actual = mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    private List<Author> getTestAuthors() {
        return List.of(new Author(1L,"1"));
    }

    private String convertToJson(List<Author> authors) throws Exception {
        List<AuthorDto> dtos = authors.stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dtos);
    }
}