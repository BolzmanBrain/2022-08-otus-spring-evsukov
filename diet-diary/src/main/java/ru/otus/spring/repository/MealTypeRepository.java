package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.MealType;

public interface MealTypeRepository extends JpaRepository<MealType, Long> {
}
