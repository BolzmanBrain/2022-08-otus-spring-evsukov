package ru.otus.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.ApplicationUser;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserProviderService {
    private final UserRepository userRepository;

    public ApplicationUser findUser(Long userId) {
        ApplicationUser user = null;
        if(userId != null) {
            var optionalUser = userRepository.findById(userId);

            if(optionalUser.isEmpty()) {
                throw new NotFoundException(String.format("User %d not found", userId));
            }
            user = optionalUser.get();
        }
        return user;
    }
}
