package ru.otus.spring.exceptions;

public class ForeignKeyViolatedException extends Exception {
    public ForeignKeyViolatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForeignKeyViolatedException(Throwable cause) {
        super(UserMessages.FOREIGN_KEY_VIOLATED, cause);
    }
}
