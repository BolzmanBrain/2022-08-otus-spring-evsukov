package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.spring.configs.AppProps;

// Same as @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
@EnableAspectJAutoProxy
public class StudentTestingApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudentTestingApplication.class, args);
	}
}
