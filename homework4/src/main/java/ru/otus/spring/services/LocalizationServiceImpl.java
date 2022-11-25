package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.LocaleProvider;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    @Override
    public String localizeMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, localeProvider.getCurrentLocale());
    }
}
