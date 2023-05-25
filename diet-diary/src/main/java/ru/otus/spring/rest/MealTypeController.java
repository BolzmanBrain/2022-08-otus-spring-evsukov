package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.FoodCategoryDto;
import ru.otus.spring.dto.MealTypeDto;
import ru.otus.spring.service.MealTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealTypeController {
    private final MealTypeService mealTypeService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/v1/meal-types")
    public List<MealTypeDto> getFoodCategory() {
        return mealTypeService.findAll().stream()
                .map(MealTypeDto::toDto).toList();
    }
}
