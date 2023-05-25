package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.NutrientStorageType;

import java.util.Optional;

public interface NutrientStorageTypeRepository extends JpaRepository<NutrientStorageType, Long> {
}
