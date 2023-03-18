package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.ApplicationUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser,Long> {
    Optional<ApplicationUser> findUserByUsername(String username);
}
