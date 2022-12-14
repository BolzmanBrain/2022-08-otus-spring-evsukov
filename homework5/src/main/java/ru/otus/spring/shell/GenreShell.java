package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreDao genreDao;
    @ShellMethod(value = "Select genres", key = {"select genre", "sg"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) Integer id) {
        if(Objects.isNull(id)) {
            return outputAllGenres();
        }
        else {
            return outputGenreById(id);
        }
    }

    private String outputAllGenres() {
        List<Genre> genres = genreDao.getAll();
        return genres.stream()
                .map(Genre::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputGenreById(int id) {
        try {
            Genre genre = genreDao.getById(id).orElseThrow();
            return genre.toString();
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @ShellMethod(value = "Insert a genre", key = {"insert genre","ig"})
    public String insert(String name) {
        Genre genre = Genre.createWithName(name);
        try {
            genreDao.insert(genre);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Update a genre", key = {"update genre","ug"})
    public String update(@ShellOption(value = {"--id","-i"}) int id,
                         @ShellOption(value = {"--name","-n"}) String name) {
        Genre updatedGenre = new Genre(id, name);
        try {
            genreDao.update(updatedGenre);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @ShellMethod(value = "Count authors", key = {"count genre","cg"})
    public String count() {
        final String TOTAL_GENRES_MESSAGE = "Total number of genres is: %d";

        int totalNumOfGenres = genreDao.count();
        return String.format(TOTAL_GENRES_MESSAGE, totalNumOfGenres);
    }

    @ShellMethod(value = "Delete a genre", key = {"delete genre", "dg"})
    public String delete(int id) {
        try {
            genreDao.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }
}
