package ru.otus.spring.page;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookPageController.class)
class BookPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetRoot_Correct() throws Exception {
        val viewName = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.BOOKS_VIEW, viewName);
    }

    @Test
    void shouldGetEditBook_Correct() throws Exception {
        val viewName = mvc.perform(get("/edit_book")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.EDIT_BOOK_VIEW, viewName);
    }

    @Test
    void shouldGetAddBook_Correct() throws Exception {
        val viewName = mvc.perform(get("/add_book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn().getModelAndView()
                .getViewName();

        assertEquals(BookPageController.ADD_BOOK_VIEW, viewName);
    }
}
