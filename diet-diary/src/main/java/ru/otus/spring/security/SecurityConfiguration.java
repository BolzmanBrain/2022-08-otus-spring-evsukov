package ru.otus.spring.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    public static final String LOGIN_URL = "/login";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( ( request ) -> request
                        .requestMatchers(HttpMethod.GET,
                                "/js/*",
                                "/api/v1/food/**",
                                "/api/v1/food-categories",
                                "/api/v1/meal-types",
                                "/api/v1/nutrient-storage-types").permitAll()
                        .requestMatchers("/",
                                "/api/v1/diet/**").hasAnyRole(Roles.USER_ROLE, Roles.SUPERUSER_ROLE)
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
