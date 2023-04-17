package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Student {
    private final String fullName;
    private final double salaryInDollars;
}
