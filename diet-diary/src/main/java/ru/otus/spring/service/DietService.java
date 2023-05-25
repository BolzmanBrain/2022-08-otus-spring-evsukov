package ru.otus.spring.service;

import ru.otus.spring.domain.Diet;

import java.util.Date;
import java.util.Optional;

public interface DietService {
    void save(Diet diet);
    void deleteById(Long id);
    Optional<Diet> findById(Long id);
    Optional<Diet> findByDateAndUserId(Date date, Long userId);
}
