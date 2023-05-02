package ru.otus.spring.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.BookCommentRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @HystrixCommand(commandKey="findByIdBookComment", fallbackMethod="buildFallbackFindById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public Optional<BookComment> findById(long id) {
        return bookCommentRepository.findById(id);
    }

    @HystrixCommand(commandKey="countBookComment", fallbackMethod="buildFallbackCount",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public long count() {
        return bookCommentRepository.count();
    }

    @HystrixCommand(commandKey="saveBookComment", fallbackMethod="buildFallbackSave",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public BookComment save(BookCommentDto bookCommentDto) {
        if (bookCommentDto.getId() == null) {
            return insert(bookCommentDto);
        } else if (bookCommentDto.getIdBook() == null) {
            return update(bookCommentDto);
        }
        throw new ConstraintViolatedException("BookComment is not filled correctly");
    }

    @HystrixCommand(commandKey="deleteByIdBookComment", fallbackMethod="buildFallbackDeleteById",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    @Override
    public void deleteById(long id) {
        Optional<BookComment> optionalBookComment = findById(id);

        if (optionalBookComment.isPresent()) {
            BookComment bookComment = optionalBookComment.get();
            bookCommentRepository.delete(bookComment);
        }
    }

    public Optional<BookComment> buildFallbackFindById(long id) {
        return Optional.empty();
    }

    public long buildFallbackCount() {
        return 0;
    }

    public BookComment buildFallbackSave(BookCommentDto bookCommentDto) {
        return null;
    }

    public void buildFallbackDeleteById(long id) {}

    private BookComment insert(BookCommentDto bookCommentDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookCommentDto.getIdBook());

        if (optionalBook.isPresent()) {
            BookComment newBookComment = BookComment.createForInsert(bookCommentDto.getText(), optionalBook.get());
            return bookCommentRepository.save(newBookComment);
        }
        throw new ConstraintViolatedException("Specified book doesn't exist");
    }

    private BookComment update(BookCommentDto bookCommentDto) {
        Optional<BookComment> optionalOldBookComment = bookCommentRepository.findById(bookCommentDto.getId());

        if (optionalOldBookComment.isPresent()) {
            Book book = optionalOldBookComment.get().getBook();
            BookComment newBookComment = new BookComment(bookCommentDto.getId(), bookCommentDto.getText(), book);
            return bookCommentRepository.save(newBookComment);
        }
        throw new ConstraintViolatedException("Trying to update a comment that doesn't exist");
    }
}
