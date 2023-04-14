package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Professional {
    private final String fullName;
    private final double salaryInDollars;
}
