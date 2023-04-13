package ru.otus.spring.service;

public interface DatastoreLifecycleService {
    void doBeforeAll();
    void doAfterAll();
}
