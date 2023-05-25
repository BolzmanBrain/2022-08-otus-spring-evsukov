package ru.otus.spring.dto;

import lombok.Data;
import ru.otus.spring.domain.NutrientStorageType;

@Data
public class NutrientStorageTypeDto {
    private final Long id;
    private final String code;
    private final String name;

    public static NutrientStorageTypeDto toDto(NutrientStorageType nutrientStorageType) {
        return new NutrientStorageTypeDto(nutrientStorageType.getId(), nutrientStorageType.getCode(), nutrientStorageType.getName());
    }

    public static NutrientStorageType toDomain(NutrientStorageTypeDto nutrientStorageTypeDto) {
        return new NutrientStorageType(nutrientStorageTypeDto.getId(), nutrientStorageTypeDto.getCode(), nutrientStorageTypeDto.getName());
    }
}
