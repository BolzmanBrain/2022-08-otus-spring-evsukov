package ru.otus.spring.domain.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {
    public final String name;
    public final String surname;

    public String getStringRepresentation() {
        return name + " " + surname;
    }
}
