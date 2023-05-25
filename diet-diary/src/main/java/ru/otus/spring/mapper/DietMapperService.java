package ru.otus.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Diet;
import ru.otus.spring.dto.DietDto;
import ru.otus.spring.service.NutritionalValueComputationStrategyProvider;

@Service
@RequiredArgsConstructor
public class DietMapperService {
    private final MealMapperService mealMapperService;
    private final UserProviderService userProviderService;
    private final NutritionalValueComputationStrategyProvider strategyProvider;

    public DietDto toDto(Diet diet) {
        var strategy = strategyProvider.getDefaultStrategy();
        var nutritionalValue = strategy.compute(diet);

        var mealDtos = diet.getMeals().stream()
                .map(mealMapperService::toDto).toList();

        return new DietDto(diet.getId(), diet.getDate(), diet.getUser().getId(), mealDtos, nutritionalValue);
    }

    public Diet toDomain(DietDto dietDto) {
        var user = userProviderService.findUser(dietDto.getUserId());
        var meals = dietDto.getMeals().stream()
                .map(mealMapperService::toDomain).toList();

        return new Diet(dietDto.getId(), dietDto.getDate(), user, meals);
    }
}
