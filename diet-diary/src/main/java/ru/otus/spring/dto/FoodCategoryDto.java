package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.FoodCategory;

@Data
public class FoodCategoryDto {
    private final Long id;
    private final String name;

    public static FoodCategory toDomain(FoodCategoryDto foodCategoryDto) {
        return new FoodCategory(foodCategoryDto.getId(), foodCategoryDto.getName());
    }

    public static FoodCategoryDto toDto(FoodCategory foodCategory) {
        return new FoodCategoryDto(foodCategory.getId(), foodCategory.getName());
    }
}
