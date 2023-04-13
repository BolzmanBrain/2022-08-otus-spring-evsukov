package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
@RequiredArgsConstructor
public class DatastoreLifecycleServiceH2 implements DatastoreLifecycleService {
    private final JdbcOperations jdbc;
    private final AppProps appProps;
    private final ResourceReaderService resourceReaderService;

    @Override
    public void doBeforeAll() {
        String sqlBefore = resourceReaderService.read(appProps.getSql_before_filename());
        jdbc.execute(sqlBefore);
    }

    @Override
    public void doAfterAll() {
        String sqlAfter = resourceReaderService.read(appProps.getSql_after_filename());
        jdbc.execute(sqlAfter);
    }
}
