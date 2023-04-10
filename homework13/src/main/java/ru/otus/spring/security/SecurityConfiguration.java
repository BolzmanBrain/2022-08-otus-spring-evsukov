package ru.otus.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {
    public static final String LOGIN_URL = "/login";

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( ( authorize ) -> authorize
                        .antMatchers(HttpMethod.GET,
                                "/actions.js",
                                "/main.css",
                                "/",
                                "/books",
                                "/api/v1/genres",
                                "/api/v1/authors",
                                "/api/v1/books/**").permitAll()
                        .antMatchers("/edit_book",
                                "/add_book").hasAnyRole(Roles.USER_ROLE, Roles.SUPERUSER_ROLE)
                        .antMatchers(HttpMethod.POST,
                                "/api/v1/books").hasAnyRole(Roles.USER_ROLE, Roles.SUPERUSER_ROLE)
                        .antMatchers(HttpMethod.PUT,
                                "/api/v1/books").hasAnyRole(Roles.USER_ROLE, Roles.SUPERUSER_ROLE)
                        .anyRequest().hasRole(Roles.SUPERUSER_ROLE)
                )
                .formLogin()
                .loginProcessingUrl(LOGIN_URL)
                .defaultSuccessUrl("/");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
