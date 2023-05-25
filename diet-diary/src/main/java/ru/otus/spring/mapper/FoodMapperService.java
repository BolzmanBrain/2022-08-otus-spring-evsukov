package ru.otus.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Food;
import ru.otus.spring.dto.FoodDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repository.FoodCategoryRepository;
import ru.otus.spring.repository.NutrientStorageTypeRepository;

@Service
@RequiredArgsConstructor
public class FoodMapperService {
    private final FoodCategoryRepository foodCategoryRepository;
    private final UserProviderService userProviderService;
    private final NutrientStorageTypeRepository nutrientStorageTypeRepository;

    public FoodDto toDto(Food food) {
        Long userId = null;

        if(food.getUser() != null) {
            userId = food.getUser().getId();
        }

        return new FoodDto(food.getId(), food.getName(), food.getCalories(), food.getProteinsInGrams(),
                food.getFatsInGrams(), food.getCarbsInGrams(), food.getNutrientStorageType().getId(),
                food.getFoodCategory().getId(), userId);
    }

    public Food toDomain(FoodDto foodDto) {
        var optionalNutrientStorageType = nutrientStorageTypeRepository.findById(foodDto.getNutrientsStorageTypeId());
        var optionalFoodCategory = foodCategoryRepository.findById(foodDto.getFoodCategoryId());
        var user = userProviderService.findUser(foodDto.getUserId());

        if(optionalFoodCategory.isEmpty()) {
            throw new NotFoundException(String.format("Food category %d not found", foodDto.getFoodCategoryId()));
        }

        if(optionalNutrientStorageType.isEmpty()) {
            throw new NotFoundException(String.format("NutrientStorageType %d not found", foodDto.getNutrientsStorageTypeId()));
        }
        return new Food(foodDto.getId(), foodDto.getName(), foodDto.getCalories(),
                foodDto.getProteinsInGrams(), foodDto.getFatsInGrams(),
                foodDto.getCarbsInGrams(), optionalNutrientStorageType.get(),
                optionalFoodCategory.get(), user);
    }
}
