package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.NutritionalValue;

import java.util.Date;
import java.util.List;

@Data
public class MealDto {
    private final Long id;
    private final Date timestamp;
    private final List<CourseDto> courses;
    private final Long mealTypeId;
    private final NutritionalValue nutritionalValue;
}
