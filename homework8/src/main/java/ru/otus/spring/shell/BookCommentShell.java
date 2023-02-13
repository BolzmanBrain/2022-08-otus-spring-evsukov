package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
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
    public String selectBookComment(String idBook, String idBookComment) {
        Optional<BookComment> optionalBookComment = bookCommentService.findByIds(idBook, idBookComment);

        if(optionalBookComment.isPresent()) {
            return optionalBookComment.get().toString();
        } else {
            return UserMessages.NO_DATA_FOUND;
        }
    }

    @ShellMethod(value = "Select book comments by book id",
    key = {"select book comment by book id","sbcb"})
    public String selectBookCommentsByBookId(String idBook) {
        Optional<Book> optionalBook = bookService.findById(idBook);

        if(optionalBook.isPresent()) {
            List<BookComment> bookComments = optionalBook.get().getComments();
            return bookComments.stream()
                    .map(BookComment::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        return UserMessages.NO_DATA_FOUND;
    }

    @ShellMethod(value = "Insert a book comment", key = {"insert book comment","ibc"})
    public String insert(@ShellOption(value = {"--book-id","-b"}) String idBook,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            BookCommentDto bookCommentDto = BookCommentDto.createForInsert(text, idBook);
            bookCommentService.save(bookCommentDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.REFERENCE_INTEGRITY_VIOLATED;
        }
    }

    @ShellMethod(value = "Update a book comment", key = {"update book comment","ubc"})
    public String update(@ShellOption(value = {"--id-book","-b"}) String idBook,
                         @ShellOption(value = {"--id-comment","-c"}) String idBookComment,
                         @ShellOption(value = {"--text","-t"}) String text) {
        try {
            BookCommentDto bookCommentDto = new BookCommentDto(idBookComment, text, idBook);
            bookCommentService.save(bookCommentDto);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.UNIQUENESS_VIOLATED;
        }
    }

    @ShellMethod(value = "Delete a book comment", key = {"delete book comment", "dbc"})
    public String delete(String idBook, String idBookComment) {
        try {
            bookCommentService.deleteByIds(idBook, idBookComment);
            return UserMessages.ACTION_EXECUTED_SUCCESSFULLY;
        }
        catch (ConsistencyViolatedException e) {
            return UserMessages.ACTION_COULD_NOT_BE_EXECUTED +". "+UserMessages.REFERENCE_INTEGRITY_VIOLATED;
        }
    }
}
