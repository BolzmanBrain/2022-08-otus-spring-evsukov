package ru.otus.spring.strategy;

import ru.otus.spring.domain.Course;
import ru.otus.spring.domain.Diet;
import ru.otus.spring.domain.Meal;
import ru.otus.spring.domain.NutritionalValue;

public interface NutritionalValueComputationStrategy {
    NutritionalValue compute(Course course);
    NutritionalValue compute(Meal meal);
    NutritionalValue compute(Diet diet);
}
