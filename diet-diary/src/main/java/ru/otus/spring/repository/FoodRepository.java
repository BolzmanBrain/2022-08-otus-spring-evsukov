package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByUser_Id(Long userId);
}
