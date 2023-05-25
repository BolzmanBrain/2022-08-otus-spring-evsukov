package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.UserService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalApplicationUser = userService.findByLogin(username);

        if(optionalApplicationUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with name: %s not found", username));
        }
        var applicationUser = optionalApplicationUser.get();

        return User.withUsername(applicationUser.getLogin())
                .password(applicationUser.getPassword())
                .roles(applicationUser.getRole().getName())
                .build();
    }
}
