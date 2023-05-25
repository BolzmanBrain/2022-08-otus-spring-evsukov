package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Diet;
import ru.otus.spring.repository.DietRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {
    private final DietRepository dietRepository;

    @Override
    public void save(Diet diet) {
        dietRepository.save(diet);
    }

    @Override
    public void deleteById(Long id) {
        dietRepository.deleteById(id);
    }

    @Override
    public Optional<Diet> findById(Long id) {
        return dietRepository.findById(id);
    }

    @Override
    public Optional<Diet> findByDateAndUserId(Date date, Long userId) {
        return dietRepository.findByDateAndUser_Id(date, userId);
    }
}
