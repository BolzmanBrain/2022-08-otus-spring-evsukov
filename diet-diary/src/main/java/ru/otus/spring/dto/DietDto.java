package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.NutritionalValue;

import java.util.Date;
import java.util.List;

@Data
public class DietDto {
    private final Long id;
    private final Date date;
    private final Long userId;
    private final List<MealDto> meals;
    private final NutritionalValue nutritionalValue;
}
