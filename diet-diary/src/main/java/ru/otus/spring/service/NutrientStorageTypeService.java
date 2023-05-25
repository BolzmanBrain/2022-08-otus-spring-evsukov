package ru.otus.spring.service;

import ru.otus.spring.domain.NutrientStorageType;

import java.util.List;

public interface NutrientStorageTypeService {
    List<NutrientStorageType> findAll();
}
