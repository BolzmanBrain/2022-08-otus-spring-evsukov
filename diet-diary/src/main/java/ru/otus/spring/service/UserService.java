package ru.otus.spring.service;

import ru.otus.spring.domain.ApplicationUser;

import java.util.Optional;

public interface UserService {
    Optional<ApplicationUser> findByLogin(String login);
}
