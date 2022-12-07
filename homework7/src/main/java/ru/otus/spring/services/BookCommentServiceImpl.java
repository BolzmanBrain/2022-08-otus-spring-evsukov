package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public Optional<BookComment> findById(long id) {
        return bookCommentRepository.findById(id);
    }

    @Override
    public long count() {
        return bookCommentRepository.count();
    }

    @Transactional
    @Override
    public BookComment save(BookCommentDto bookCommentDto) {
        if (bookCommentDto.getId() == null) {
            return insert(bookCommentDto);
        } else if (bookCommentDto.getIdBook() == null) {
            return update(bookCommentDto);
        }
        throw new ConstraintViolatedException("BookComment is not filled correctly");
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Optional<BookComment> optionalBookComment = findById(id);

        if (optionalBookComment.isPresent()) {
            BookComment bookComment = optionalBookComment.get();
            bookCommentRepository.delete(bookComment);
        }
    }

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
