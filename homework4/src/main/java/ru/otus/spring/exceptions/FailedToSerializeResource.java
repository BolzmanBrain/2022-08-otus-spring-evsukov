package ru.otus.spring.exceptions;

public class FailedToSerializeResource extends RuntimeException {
    public FailedToSerializeResource(String message, Throwable cause) {
        super(message, cause);
    }
}
