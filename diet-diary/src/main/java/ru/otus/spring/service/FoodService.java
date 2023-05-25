package ru.otus.spring.service;

import ru.otus.spring.domain.Food;

import java.util.List;
import java.util.Optional;

public interface FoodService {
    void save(Food food);
    void deleteById(Long id);
    Optional<Food> findById(Long id);
    List<Food> findAllAvailableForUserId(Long userId);
}
