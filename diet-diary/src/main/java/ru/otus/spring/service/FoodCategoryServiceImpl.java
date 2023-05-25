package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.FoodCategory;
import ru.otus.spring.repository.FoodCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {
    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public List<FoodCategory> findAll() {
        return foodCategoryRepository.findAll();
    }
}
