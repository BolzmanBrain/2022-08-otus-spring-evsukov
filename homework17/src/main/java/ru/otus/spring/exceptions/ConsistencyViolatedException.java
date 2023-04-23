package ru.otus.spring.exceptions;

public class ConsistencyViolatedException extends RuntimeException {
    public ConsistencyViolatedException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConsistencyViolatedException(Throwable cause) {
        super(UserMessages.CONSTRAINT_VIOLATED, cause);
    }
    public ConsistencyViolatedException(String message) {
        super(message);
    }
}
