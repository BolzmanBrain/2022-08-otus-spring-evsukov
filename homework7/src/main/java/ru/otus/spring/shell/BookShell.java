package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.services.BookService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookService bookService;

    @ShellMethod(value = "Select books", key = {"select book", "sb"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Long id) {
        if(Objects.isNull(id)) {
            return outputAllBooks();
        }
        else {
            return outputBookById(id);
        }
    }

    @ShellMethod(value = "Insert a book", key = {"insert book","ib"})
    public String insert(@ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) Long idAuthor,
                         @ShellOption(value = {"--genre-id","-g"}) Long idGenre) {
        try {
            BookDto bookDto = BookDto.createForInsert(name, idAuthor, idGenre);
            bookService.save(bookDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConstraintViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Update a book", key = {"update book","ub"})
    public String update(@ShellOption(value = {"--id","-i"}) long idBook,
                         @ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) long idAuthor,
                         @ShellOption(value = {"--genre-id","-g"}) long idGenre) {
        try {
            BookDto bookDto = new BookDto(idBook, name, idAuthor, idGenre);
            bookService.save(bookDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConstraintViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Count books", key = {"count book","cb"})
    public String count() {
        final String TOTAL_BOOKS_MESSAGE = "Total number of books is: %d";

        long totalNumOfBooks = bookService.count();
        return String.format(TOTAL_BOOKS_MESSAGE, totalNumOfBooks);
    }

    @ShellMethod(value = "Delete a book", key = {"delete book", "db"})
    public String delete(long id) {
        bookService.deleteById(id);
        return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
    }

    private String outputAllBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(Book::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputBookById(long id) {
        Optional<Book> optionalBook = bookService.findById(id);

        if(optionalBook.isPresent()) {
            return optionalBook.get().toString();
        } else {
            return UserMessages.NO_DATA_FOUND;
        }
    }
}
