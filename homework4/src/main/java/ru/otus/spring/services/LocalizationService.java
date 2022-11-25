package ru.otus.spring.services;

public interface LocalizationService {
    String localizeMessage(String message, Object... args);
}
