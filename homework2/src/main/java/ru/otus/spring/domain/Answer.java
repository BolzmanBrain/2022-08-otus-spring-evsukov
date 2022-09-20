package ru.otus.spring.domain;

public interface Answer {
    boolean isEqual(Answer otherAnswer);

    String getStringRepresentation();
}
