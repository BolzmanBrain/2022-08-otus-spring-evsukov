package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.NutrientStorageType;
import ru.otus.spring.repository.NutrientStorageTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutrientStorageTypeServiceImpl implements NutrientStorageTypeService{
    private final NutrientStorageTypeRepository nutrientStorageTypeRepository;

    @Override
    public List<NutrientStorageType> findAll() {
        return nutrientStorageTypeRepository.findAll();
    }
}
