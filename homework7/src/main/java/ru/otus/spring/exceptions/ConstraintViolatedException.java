package ru.otus.spring.exceptions;

public class ConstraintViolatedException extends RuntimeException {
    public ConstraintViolatedException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConstraintViolatedException(Throwable cause) {
        super(UserMessages.CONSTRAINT_VIOLATED, cause);
    }
    public ConstraintViolatedException(String message) {
        super(message);
    }
}
