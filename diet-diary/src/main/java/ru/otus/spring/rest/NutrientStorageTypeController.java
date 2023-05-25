package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.NutrientStorageTypeDto;
import ru.otus.spring.service.NutrientStorageTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NutrientStorageTypeController {
    private final NutrientStorageTypeService nutrientStorageTypeService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/v1/nutrient-storage-types")
    public List<NutrientStorageTypeDto> getNutrientStorageTypes() {
        return nutrientStorageTypeService.findAll().stream()
                .map(NutrientStorageTypeDto::toDto).toList();
    }
}
