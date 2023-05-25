package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.NutritionalValue;

@Data
public class CourseDto {
    private final Long id;
    private final Double quantity;
    private final Long foodId;
    private final NutritionalValue nutritionalValue;
}