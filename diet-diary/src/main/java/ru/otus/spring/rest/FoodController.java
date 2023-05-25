package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.FoodDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.mapper.FoodMapperService;
import ru.otus.spring.service.FoodService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class FoodController {
    private final FoodService foodService;
    private final FoodMapperService foodMapperService;

    @GetMapping("/api/v1/food")
    public List<FoodDto> getFood() {
        return foodService.findAllAvailableForUserId(null)
                .stream().map(foodMapperService::toDto)
                .toList();
    }

    @GetMapping("/api/v1/food/{id}")
    public FoodDto getFoodById(@PathVariable Long id) {
        return foodService.findById(id)
                .map(foodMapperService::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/api/v1/food")
    public void postFood(@RequestBody FoodDto foodDto) {
        var food = foodMapperService.toDomain(foodDto);
        foodService.save(food);
    }

    @PutMapping("/api/v1/food")
    public void putFood(@RequestBody FoodDto foodDto) {
        var food = foodMapperService.toDomain(foodDto);
        foodService.save(food);
    }

    @DeleteMapping("/api/v1/food/{id}")
    public void deleteFoodById(@PathVariable Long id) {
        foodService.deleteById(id);
    }
}
