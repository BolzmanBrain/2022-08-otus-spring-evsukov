package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.services.BookCommentService;
import ru.otus.spring.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommentShell {
    private final BookService bookService;
    private final BookCommentService bookCommentService;

    @ShellMethod(value = "Select a book comment by id", key = {"select book comment","sbc"})
    public String selectBookComment(Long id) {
        Optional<BookComment> optionalBookComment = bookCommentService.findById(id);

        if(optionalBookComment.isPresent()) {
            return optionalBookComment.get().toString();
        } else {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @ShellMethod(value = "Select book comments by book id",
    key = {"select book comment by book id","sbcb"})
    public String selectBookCommentsByBookId(Long idBook) {
        Optional<Book> optionalBook = bookService.findById(idBook);

        if(optionalBook.isPresent()) {
            List<BookComment> bookComments = optionalBook.get().getComments();
            return bookComments.stream()
                    .map(BookComment::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        return UserMessages.NO_DATA_FOUND;
    }

    @ShellMethod(value = "Insert a book", key = {"insert book comment","ibc"})
    public String insert(@ShellOption(value = {"--book-id","-b"}) Long idBook,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            BookCommentDto bookCommentDto = BookCommentDto.of(text, idBook);
            bookCommentService.save(bookCommentDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConstraintViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @Transactional
    @ShellMethod(value = "Update a book comment", key = {"update book comment","ubc"})
    public String update(@ShellOption(value = {"--id","-i"}) long id,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            BookCommentDto bookCommentDto = new BookCommentDto(id, text, null);
            bookCommentService.save(bookCommentDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConstraintViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Count book comments", key = {"count book comments","cbc"})
    public String count() {
        final String TOTAL_AUTHORS_MESSAGE = "Total number of book comments is: %d";

        long totalNumOfBookComments = bookCommentService.count();
        return String.format(TOTAL_AUTHORS_MESSAGE, totalNumOfBookComments);
    }

    @Transactional
    @ShellMethod(value = "Delete a book comment", key = {"delete book comment", "dbc"})
    public String delete(long id) {
        try {
            bookCommentService.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConstraintViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }
}
