package ru.otus.spring.exceptions;

public class UnknownQuestionTypeIsProvided extends RuntimeException {
    public UnknownQuestionTypeIsProvided(String message, Throwable cause) {
        super(message, cause);
    }
}
