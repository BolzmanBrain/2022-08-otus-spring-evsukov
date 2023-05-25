package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.MealType;
import ru.otus.spring.repository.MealTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealTypeServiceImpl implements MealTypeService {
    private final MealTypeRepository mealTypeRepository;

    @Override
    public List<MealType> findAll() {
        return mealTypeRepository.findAll();
    }
}
