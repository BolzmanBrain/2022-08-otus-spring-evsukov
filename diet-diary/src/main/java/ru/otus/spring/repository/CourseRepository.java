package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
