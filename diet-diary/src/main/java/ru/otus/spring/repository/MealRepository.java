package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
