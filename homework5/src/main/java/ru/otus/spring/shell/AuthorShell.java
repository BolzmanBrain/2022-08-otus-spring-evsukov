package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;
import ru.otus.spring.presentation.AuthorToStringConvertor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorDao authorDao;
    private final AuthorToStringConvertor authorToStringConvertor;

    @ShellMethod(value = "Select authors", key = {"select author", "sa"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Integer id) {
        if(Objects.isNull(id)) {
            return outputAllAuthors();
        }
        else {
            return outputAuthorById(id);
        }
    }

    private String outputAllAuthors() {
        List<Author> authors = authorDao.getAll();
        return authors.stream()
                .map(authorToStringConvertor::convertToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputAuthorById(int id) {
        try {
            Author author = authorDao.getById(id).orElseThrow();
            return authorToStringConvertor.convertToString(author);
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @ShellMethod(value = "Insert an author", key = {"insert author","ia"})
    public String insert(String name) {
        Author author = Author.createWithNullId(name);
        try {
            authorDao.insert(author);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Update an author", key = {"update author","ua"})
    public String update(@ShellOption(value = {"--id","-i"}) int id,
                         @ShellOption(value = {"--name","-n"}) String name) {
        Author updatedAuthor = new Author(id, name);
        try {
            authorDao.update(updatedAuthor);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Count authors", key = {"count author","ca"})
    public String count() {
        final String TOTAL_AUTHORS_MESSAGE = "Total number of authors is: %d";

        int totalNumOfAuthors = authorDao.count();
        return String.format(TOTAL_AUTHORS_MESSAGE, totalNumOfAuthors);
    }

    @ShellMethod(value = "Delete an author", key = {"delete author", "da"})
    public String delete(int id) {
        try {
            authorDao.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }
}
