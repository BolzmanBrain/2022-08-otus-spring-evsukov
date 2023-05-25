package ru.otus.spring.service;

import ru.otus.spring.domain.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    List<FoodCategory> findAll();
}
