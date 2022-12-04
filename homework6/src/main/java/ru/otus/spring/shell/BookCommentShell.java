package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;
import ru.otus.spring.exceptions.UserMessages;
import ru.otus.spring.repository.BookCommentRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommentShell {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Select a book comment by id", key = {"select book comment","sbc"})
    public String selectBookComment(Long id) {
        try {
            BookComment bookComment = bookCommentRepository.getById(id).orElseThrow();
            return bookComment.convertToString();
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Select book comments by book id",
    key = {"select book comment by book id","sbcb"})
    public String selectBookCommentsByBookId(Long bookId) {
        List<BookComment> bookComments = bookCommentRepository.getByBookId(bookId);
        return bookComments.stream()
                .map(BookComment::convertToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Transactional
    @ShellMethod(value = "Insert a book", key = {"insert book comment","ibc"})
    public String insert(@ShellOption(value = {"--book-id","-b"}) Long idBook,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            Book book = bookRepository.getById(idBook).orElseThrow();
            BookComment newBookComment = BookComment.of(text, book);
            bookCommentRepository.save(newBookComment);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (NoSuchElementException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.NO_DATA_FOUND;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }

    @Transactional
    @ShellMethod(value = "Update a book comment", key = {"update book comment","ubc"})
    public String update(@ShellOption(value = {"--id","-i"}) long id,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            BookComment oldBookComment = bookCommentRepository.getById(id).orElseThrow();
            BookComment newBookComment = new BookComment(id, text, oldBookComment.getBook());
            bookCommentRepository.save(newBookComment);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (NoSuchElementException e) {
            return UserMessages.NO_DATA_FOUND;
        }
        catch (UniqueKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUE_KEY_VIOLATED;
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Count book comments", key = {"count book comments","cbc"})
    public String count() {
        final String TOTAL_AUTHORS_MESSAGE = "Total number of book comments is: %d";

        long totalNumOfBookComments = bookCommentRepository.count();
        return String.format(TOTAL_AUTHORS_MESSAGE, totalNumOfBookComments);
    }

    @Transactional
    @ShellMethod(value = "Delete a book comment", key = {"delete book comment", "dbc"})
    public String delete(long id) {
        try {
            bookCommentRepository.deleteById(id);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ForeignKeyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.FOREIGN_KEY_VIOLATED;
        }
    }
}
