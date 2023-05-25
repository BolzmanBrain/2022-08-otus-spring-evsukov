package ru.otus.spring.domain;

import lombok.Data;

@Data
public class NutritionalValue {
    private final double calories;
    private final double proteinsInGrams;
    private final double fatsInGrams;
    private final double carbsInGrams;
}
