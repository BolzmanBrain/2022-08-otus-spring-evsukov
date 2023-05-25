package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.MealType;

@Data
public class MealTypeDto {
    private final Long id;
    private final String name;

    public static MealTypeDto toDto(MealType mealType) {
        return new MealTypeDto(mealType.getId(), mealType.getName());
    }

    public static MealType toDomain(MealTypeDto mealTypeDto) {
        return new MealType(mealTypeDto.getId(), mealTypeDto.getName());
    }
}
