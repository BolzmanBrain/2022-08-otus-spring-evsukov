package ru.otus.spring.dto;

import lombok.Data;

@Data
public class FoodDto {
    private final Long id;
    private final String name;
    private final Integer calories;
    private final Integer proteinsInGrams;
    private final Integer fatsInGrams;
    private final Integer carbsInGrams;
    private final Long nutrientsStorageTypeId;
    private final Long foodCategoryId;
    private final Long userId;
}
