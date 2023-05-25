package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
