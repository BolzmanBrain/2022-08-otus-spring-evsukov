package ru.otus.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Meal;
import ru.otus.spring.dto.MealDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repository.MealTypeRepository;
import ru.otus.spring.service.NutritionalValueComputationStrategyProvider;

@Service
@RequiredArgsConstructor
public class MealMapperService {
    private final CourseMapperService courseMapperService;
    private final MealTypeRepository mealTypeRepository;
    private final NutritionalValueComputationStrategyProvider strategyProvider;

    public MealDto toDto(Meal meal) {
        var strategy = strategyProvider.getDefaultStrategy();
        var nutritionalValue = strategy.compute(meal);

        var courseDtos = meal.getCourses().stream()
                .map(courseMapperService::toDto).toList();

        return new MealDto(meal.getId(), meal.getTimestamp(),
                courseDtos, meal.getMealType().getId(), nutritionalValue);
    }

    public Meal toDomain(MealDto mealDto) {
        var optionalMealType = mealTypeRepository.findById(mealDto.getMealTypeId());

        if(optionalMealType.isEmpty()) {
            throw new NotFoundException(String.format("Meal type %d not found", mealDto.getMealTypeId()));
        }
        var courses = mealDto.getCourses().stream()
                .map(courseMapperService::toDomain).toList();

        return new Meal(mealDto.getId(), mealDto.getTimestamp(), courses, optionalMealType.get());
    }
}
