package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.services.AuthorService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorService authorService;

    @ShellMethod(value = "Select authors", key = {"select author", "sa"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) String id) {
        if (Objects.isNull(id)) {
            return outputAllAuthors();
        } else {
            return outputAuthorById(id);
        }
    }

    @ShellMethod(value = "Insert an author", key = {"insert author", "ia"})
    public String insert(String name) {
        try {
            Author author = Author.createForInsert(name);
            authorService.save(author);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        } catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED + ". " + UserMessages.UNIQUENESS_VIOLATED;
        }
    }

    @ShellMethod(value = "Update an author", key = {"update author", "ua"})
    public String update(@ShellOption(value = {"--id", "-i"}) String id,
                         @ShellOption(value = {"--name", "-n"}) String name) {
        try {
            Author updatedAuthor = new Author(id, name);
            authorService.save(updatedAuthor);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        } catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED + ". " + UserMessages.UNIQUENESS_VIOLATED;
        }
    }

    @ShellMethod(value = "Count authors", key = {"count author", "ca"})
    public String count() {
        final String TOTAL_AUTHORS_MESSAGE = "Total number of authors is: %d";

        long totalNumOfAuthors = authorService.count();
        return String.format(TOTAL_AUTHORS_MESSAGE, totalNumOfAuthors);
    }

    @ShellMethod(value = "Delete an author", key = {"delete author", "da"})
    public String delete(String id) {
        try {
            authorService.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        } catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED + ". " + UserMessages.REFERENCE_INTEGRITY_VIOLATED;
        }
    }

    private String outputAllAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream()
                .map(Author::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputAuthorById(String id) {
        Optional<Author> optionalAuthor = authorService.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get().toString();
        }
        return UserMessages.NO_DATA_FOUND;
    }
}
