package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Professional;
import ru.otus.spring.domain.Student;

@Service
public class OtusService implements OnlineSchoolService {

    @Override
    public Professional educate(Student student) {
        delay();
        return new Professional(student.getFullName(), student.getSalaryInDollars() * 2);
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
