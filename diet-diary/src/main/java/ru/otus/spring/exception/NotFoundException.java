package ru.otus.spring.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundException(Throwable cause) {
        super("Not found!", cause);
    }
    public NotFoundException() {
        super("Not found!");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
