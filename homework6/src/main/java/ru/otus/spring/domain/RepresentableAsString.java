package ru.otus.spring.domain;

public interface RepresentableAsString {
    // Some domain objects' toString() is overridden by Hibernate for technical purposes,
    // hence this custom method for printing out objects for users with.
    String convertToString();
}
