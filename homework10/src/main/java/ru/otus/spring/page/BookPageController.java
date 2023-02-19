package ru.otus.spring.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookPageController {
    public final static String BOOKS_VIEW = "books";
    public final static String EDIT_BOOK_VIEW = "edit_book";
    public final static String ADD_BOOK_VIEW = "add_book";

    @GetMapping(value = {"/","/books"})
    public String booksPage() {
        return BOOKS_VIEW;
    }

    @GetMapping("/edit_book")
    public String editBookPage(@RequestParam("id") long id) {
        return EDIT_BOOK_VIEW;
    }

    @GetMapping("/add_book")
    public String addBookPage() {
        return ADD_BOOK_VIEW;
    }
}
