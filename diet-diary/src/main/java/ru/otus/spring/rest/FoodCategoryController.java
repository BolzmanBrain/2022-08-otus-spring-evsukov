package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.FoodCategoryDto;
import ru.otus.spring.service.FoodCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodCategoryController {
    private final FoodCategoryService foodCategoryService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/v1/food-categories")
    public List<FoodCategoryDto> getFoodCategory() {
        return foodCategoryService.findAll().stream()
                .map(FoodCategoryDto::toDto).toList();
    }
}
