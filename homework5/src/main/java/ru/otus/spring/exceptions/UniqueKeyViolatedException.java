package ru.otus.spring.exceptions;

public class UniqueKeyViolatedException extends RuntimeException {
    public UniqueKeyViolatedException(String message, Throwable cause) {
        super(message, cause);
    }
    public UniqueKeyViolatedException(Throwable cause) {
        super(UserMessages.UNIQUE_KEY_VIOLATED, cause);
    }
}
