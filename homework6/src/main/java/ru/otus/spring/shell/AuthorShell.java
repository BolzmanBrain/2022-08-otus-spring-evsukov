package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Select authors", key = {"select author", "sa"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Long id) {
        if(Objects.isNull(id)) {
            return outputAllAuthors();
        }
        else {
            return outputAuthorById(id);
        }
    }

    private String outputAllAuthors() {
        List<Author> authors = authorRepository.getAll();
        return authors.stream()
                .map(Author::convertToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputAuthorById(long id) {
        try {
            Author author = authorRepository.getById(id).orElseThrow();
            return author.convertToString();
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @Transactional
    @ShellMethod(value = "Insert an author", key = {"insert author","ia"})
    public String insert(String name) {
        Author author = Author.createWithName(name);
        try {
            authorRepository.save(author);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @Transactional
    @ShellMethod(value = "Update an author", key = {"update author","ua"})
    public String update(@ShellOption(value = {"--id","-i"}) long id,
                         @ShellOption(value = {"--name","-n"}) String name) {
        Author updatedAuthor = new Author(id, name);
        try {
            authorRepository.save(updatedAuthor);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Count authors", key = {"count author","ca"})
    public String count() {
        final String TOTAL_AUTHORS_MESSAGE = "Total number of authors is: %d";

        long totalNumOfAuthors = authorRepository.count();
        return String.format(TOTAL_AUTHORS_MESSAGE, totalNumOfAuthors);
    }

    @Transactional
    @ShellMethod(value = "Delete an author", key = {"delete author", "da"})
    public String delete(long id) {
        try {
            authorRepository.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (Exception e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }
}
