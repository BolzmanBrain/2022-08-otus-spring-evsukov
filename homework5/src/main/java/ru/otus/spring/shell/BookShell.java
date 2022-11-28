package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.dto.BookDto;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.presentation.BookToStringConvertor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookDao bookDao;
    private final BookToStringConvertor bookToStringConvertor;

    @ShellMethod(value = "Select books", key = {"select book", "sb"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Integer id) {
        if(Objects.isNull(id)) {
            return outputAllBooks();
        }
        else {
            return outputBookById(id);
        }
    }

    private String outputAllBooks() {
        List<Book> books = bookDao.getAll();
        return books.stream()
                .map(bookToStringConvertor::convertToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputBookById(int id) {
        try {
            Book book = bookDao.getById(id).orElseThrow();
            return bookToStringConvertor.convertToString(book);
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @ShellMethod(value = "Insert a book", key = {"insert book","ib"})
    public String insert(@ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) Integer authorId,
                         @ShellOption(value = {"--genre-id","-g"}) Integer genreId) {
        BookDto bookDto = BookDto.createWithNullId(name, authorId,genreId);
        try {
            bookDao.insert(bookDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Update a book", key = {"update book","ub"})
    public String update(@ShellOption(value = {"--id","-i"}) int idBook,
                         @ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) int idAuthor,
                         @ShellOption(value = {"--genre-id","-g"}) int idGenre) {
        BookDto updatedBookDto = new BookDto(idBook, name, idAuthor, idGenre);
        try {
            bookDao.update(updatedBookDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Count books", key = {"count book","cb"})
    public String count() {
        final String TOTAL_BOOKS_MESSAGE = "Total number of books is: %d";

        int totalNumOfBooks = bookDao.count();
        return String.format(TOTAL_BOOKS_MESSAGE, totalNumOfBooks);
    }

    @ShellMethod(value = "Delete a book", key = {"delete book", "db"})
    public String delete(int id) {
            bookDao.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
    }
}
