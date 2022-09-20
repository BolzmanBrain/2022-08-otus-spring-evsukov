package ru.otus.spring.model;

public interface Answer {
    boolean isEqual(Answer otherAnswer);

    String getStringRepresentation();
}
