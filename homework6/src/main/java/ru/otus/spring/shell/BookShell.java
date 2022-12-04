package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Select books", key = {"select book", "sb"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Long id) {
        if(Objects.isNull(id)) {
            return outputAllBooks();
        }
        else {
            return outputBookById(id);
        }
    }

    private String outputAllBooks() {
        List<Book> books = bookRepository.getAll();
        return books.stream()
                .map(Book::convertToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputBookById(long id) {
        try {
            Book book = bookRepository.getById(id).orElseThrow();
            return book.convertToString();
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @Transactional
    @ShellMethod(value = "Insert a book", key = {"insert book","ib"})
    public String insert(@ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) Long authorId,
                         @ShellOption(value = {"--genre-id","-g"}) Long genreId) {
        Author author = Author.createWithId(authorId);
        Genre genre = Genre.createWithId(genreId);
        Book book = Book.of(name, author,genre);
        try {
            bookRepository.save(book);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @Transactional
    @ShellMethod(value = "Update a book", key = {"update book","ub"})
    public String update(@ShellOption(value = {"--id","-i"}) long idBook,
                         @ShellOption(value = {"--name","-n"}) String name,
                         @ShellOption(value = {"--author-id","-a"}) long idAuthor,
                         @ShellOption(value = {"--genre-id","-g"}) long idGenre) {
        Author author = Author.createWithId(idAuthor);
        Genre genre = Genre.createWithId(idGenre);
        Book updatedBook = new Book(idBook, name, author, genre);
        try {
            bookRepository.save(updatedBook);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Count books", key = {"count book","cb"})
    public String count() {
        final String TOTAL_BOOKS_MESSAGE = "Total number of books is: %d";

        long totalNumOfBooks = bookRepository.count();
        return String.format(TOTAL_BOOKS_MESSAGE, totalNumOfBooks);
    }

    @Transactional
    @ShellMethod(value = "Delete a book", key = {"delete book", "db"})
    public String delete(long id) {
        bookRepository.deleteById(id);
        return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
    }
}
