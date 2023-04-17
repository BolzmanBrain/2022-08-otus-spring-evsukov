package ru.otus.spring.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Professional;
import ru.otus.spring.domain.Student;

@MessagingGateway
public interface OnlineSchoolGateway {
    @Gateway(requestChannel = "studentsChannel", replyChannel = "professionalsChannel")
    Professional process(Student student);
}
