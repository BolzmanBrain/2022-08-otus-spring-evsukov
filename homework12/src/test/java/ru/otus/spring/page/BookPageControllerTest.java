package ru.otus.spring.page;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.LibraryApplication;
import ru.otus.spring.security.SecurityConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookPageController.class)
@ContextConfiguration(classes = { LibraryApplication.class, SecurityConfiguration.class })
class BookPageControllerTest {
    @Autowired
    private MockMvc mvc;

    private static final String USERNAME = "user";
    private static final String USER_ROLE = "user";
    private static final String REDIRECT_URL = "http://localhost" + SecurityConfiguration.LOGIN_URL;

    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldGetRoot_Authenticated() throws Exception {
        val viewName = mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.BOOKS_VIEW, viewName);
    }

    @WithAnonymousUser
    @Test
    void shouldGetRoot_Anonymous() throws Exception {
        val viewName = mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.BOOKS_VIEW, viewName);
    }


    @WithMockUser(username = USERNAME, authorities = USER_ROLE)
    @Test
    void shouldGetEditBook_Authenticated() throws Exception {
        val viewName = mvc.perform(get("/edit_book")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.EDIT_BOOK_VIEW, viewName);
    }

    @WithAnonymousUser
    @Test
    void shouldGetEditBook_Anonymous() throws Exception {
        mvc.perform(get("/edit_book").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REDIRECT_URL));
    }

    @WithMockUser(username = USERNAME,authorities = USER_ROLE)
    @Test
    void shouldGetAddBook_Authenticated() throws Exception {
        val viewName = mvc.perform(get("/add_book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.ADD_BOOK_VIEW, viewName);
    }

    @WithAnonymousUser
    @Test
    void shouldGetAddBook_Anonymous() throws Exception {
        mvc.perform(get("/add_book"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REDIRECT_URL));
    }
}
