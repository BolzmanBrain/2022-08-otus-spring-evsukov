package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Diet;

import java.util.Date;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Optional<Diet> findByDateAndUser_Id(Date date, Long userId);
}
