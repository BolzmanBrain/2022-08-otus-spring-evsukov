package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.exceptions.ConstraintViolatedException;
import ru.otus.spring.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final BookRepository bookRepository;

    @Override
    public Optional<BookComment> findByIds(String idBook, String idBookComment) {
        return bookRepository.findById(idBook)
                .map(Book::getComments)
                .orElse(List.of())
                .stream()
                .filter(bookComment -> bookComment.getId().equals(idBookComment))
                .findFirst();
    }

    @Override
    public BookComment save(BookCommentDto bookCommentDto) {
        if (bookCommentDto.getId() == null) {
            return insert(bookCommentDto);
        } else {
            return update(bookCommentDto);
        }
    }

    @Override
    public void deleteByIds(String idBook, String idBookComment) {
        Optional<Book> bookOptional = bookRepository.findById(idBook);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            List<BookComment> bookComments = book.getComments()
                    .stream()
                    .filter(bookComment -> !bookComment.getId().equals(idBookComment))
                    .collect(Collectors.toList());
            book.setComments(bookComments);
            bookRepository.save(book);
        } else {
            throw new ConstraintViolatedException("Wrong idBook specified");
        }
    }

    private BookComment insert(BookCommentDto bookCommentDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookCommentDto.getIdBook());

        if (optionalBook.isPresent()) {
            BookComment newBookComment = BookComment.createForInsert(bookCommentDto.getText());
            Book book = optionalBook.get();
            book.getComments().add(newBookComment);
            bookRepository.save(book);
            return newBookComment;
        } else {
            throw new ConstraintViolatedException("Specified book doesn't exist");
        }
    }

    private BookComment update(BookCommentDto bookCommentDto) {
        try {
            Book book = bookRepository.findById(bookCommentDto.getIdBook()).orElseThrow();

            BookComment bookComment = book.getComments().stream()
                    .filter(bk -> bk.getId().equals(bookCommentDto.getId()))
                    .findFirst()
                    .orElseThrow();
            bookComment.setText(bookCommentDto.getText());
            bookRepository.save(book);
            return bookComment;
        } catch (NoSuchElementException e) {
            throw new ConstraintViolatedException("Trying to update a comment that doesn't exist", e);
        }
    }
}
