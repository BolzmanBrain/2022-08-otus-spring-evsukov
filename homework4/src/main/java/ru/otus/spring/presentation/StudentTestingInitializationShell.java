package ru.otus.spring.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.configs.AppMessageCodes;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.services.StudentTestingService;

@ShellComponent
@RequiredArgsConstructor
public class StudentTestingInitializationShell implements StudentTestingInitialization {
    private final StudentTestingService testingService;
    private final MessageSource messageSource;
    private final AppProps appProps;

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
        return localize(AppMessageCodes.TEST_WELCOME, name, surname);
    }

    private Availability isLoggedIn() {
        String notLoggedInMessage = localize(AppMessageCodes.TEST_NOT_LOGGED_IN);
        return user == null ? Availability.unavailable(notLoggedInMessage) : Availability.available();
    }

    private String localize(String messageCode, String ... args) {
        return messageSource.getMessage(messageCode, args, appProps.getLocale());
    }
}
