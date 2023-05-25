package ru.otus.spring.service;

import ru.otus.spring.domain.MealType;

import java.util.List;

public interface MealTypeService {
    List<MealType> findAll();
}
