package ru.otus.spring.service;

import ru.otus.spring.domain.Professional;
import ru.otus.spring.domain.Student;

public interface OnlineSchoolService {
    Professional educate(Student student);
}
