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
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@ContextConfiguration(classes = { LibraryApplication.class, SecurityConfiguration.class })
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;

    @WithAnonymousUser
    @Test
    void shouldGetGenres_Anonymous() throws Exception {
        val genres = getTestGenres();
        given(genreService.findAll()).willReturn(genres);

        val expected = convertToJson(genres);
        val actual = mvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    private List<Genre> getTestGenres() {
        return List.of(new Genre(1L,"1"));
    }

    private String convertToJson(List<Genre> genres) throws Exception {
        List<GenreDto> dtos = genres.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dtos);
    }
}