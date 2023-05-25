package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Food;
import ru.otus.spring.repository.FoodRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public void save(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Optional<Food> findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public List<Food> findAllAvailableForUserId(Long userId) {
        return foodRepository.findByUser_Id(userId);
    }
}
