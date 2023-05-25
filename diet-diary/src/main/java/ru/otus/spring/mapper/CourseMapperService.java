package ru.otus.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Course;
import ru.otus.spring.dto.CourseDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repository.FoodRepository;
import ru.otus.spring.service.NutritionalValueComputationStrategyProvider;

@Service
@RequiredArgsConstructor
public class CourseMapperService {
    private final FoodRepository foodRepository;
    private final NutritionalValueComputationStrategyProvider strategyProvider;

    public CourseDto toDto(Course course) {
        var strategy = strategyProvider.getDefaultStrategy();
        var nutritionalValue = strategy.compute(course);

        return new CourseDto(course.getId(), course.getQuantity(),
                course.getFood().getId(), nutritionalValue);
    }

    public Course toDomain(CourseDto courseDto) {
        var optionalFood = foodRepository.findById(courseDto.getFoodId());

        if(optionalFood.isEmpty()) {
            throw new NotFoundException(String.format("Food %d not found", courseDto.getFoodId()));
        }
        return new Course(courseDto.getId(), courseDto.getQuantity(), optionalFood.get());
    }
}
