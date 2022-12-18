package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.services.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreService genreService;

    @ShellMethod(value = "Select genres", key = {"select genre", "sg"})
    public String select(@ShellOption(defaultValue = ShellOption.NULL) String id) {
        if(Objects.isNull(id)) {
            return outputAllGenres();
        }
        else {
            return outputGenreById(id);
        }
    }

    @ShellMethod(value = "Insert a genre", key = {"insert genre","ig"})
    public String insert(String name) {
        try {
            Genre genre = Genre.createForInsert(name);
            genreService.save(genre);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUENESS_VIOLATED;
        }
    }

    @ShellMethod(value = "Update a genre", key = {"update genre","ug"})
    public String update(@ShellOption(value = {"--id","-i"}) String id,
                         @ShellOption(value = {"--name","-n"}) String name) {
        try {
            Genre updatedGenre = new Genre(id, name);
            genreService.save(updatedGenre);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUENESS_VIOLATED;
        }
    }

    @ShellMethod(value = "Count authors", key = {"count genre","cg"})
    public String count() {
        final String TOTAL_GENRES_MESSAGE = "Total number of genres is: %d";

        long totalNumOfGenres = genreService.count();
        return String.format(TOTAL_GENRES_MESSAGE, totalNumOfGenres);
    }

    @ShellMethod(value = "Delete a genre", key = {"delete genre", "dg"})
    public String delete(String id) {
        try {
            genreService.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.REFERENCE_INTEGRITY_VIOLATED;
        }
    }

    private String outputAllGenres() {
        List<Genre> genres = genreService.findAll();
        return genres.stream()
                .map(Genre::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String outputGenreById(String id) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if(optionalGenre.isPresent()) {
            return optionalGenre.get().toString();
        }
        return UserMessages.NO_DATA_FOUND;
    }
}
