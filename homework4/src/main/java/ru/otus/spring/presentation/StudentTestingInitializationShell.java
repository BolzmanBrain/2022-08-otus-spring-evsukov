package ru.otus.spring.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.services.LocalizationService;
import ru.otus.spring.services.StudentTestingService;

@ShellComponent
@RequiredArgsConstructor
public class StudentTestingInitializationShell implements StudentTestingInitialization {
    private final StudentTestingService testingService;
    private final LocalizationService localizationService;
    private User user;

    @Override
    @ShellMethod(value = "Start command", key = {"s", "start"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void start() {
        testingService.run(user);
    }

    @Override
    @ShellMethod(value = "Login command", key = {"l","login"})
    public String login(String name, String surname) {
        this.user = new User(name, surname);
        return localizationService.localizeMessage(TEST_WELCOME, name, surname);
    }

    private Availability isLoggedIn() {
        String notLoggedInMessage = localizationService.localizeMessage(TEST_NOT_LOGGED_IN);
        return user == null ? Availability.unavailable(notLoggedInMessage) : Availability.available();
    }
    private static final String TEST_WELCOME = "test.welcome";
    private static final String TEST_NOT_LOGGED_IN = "test.not_logged_in";
}
