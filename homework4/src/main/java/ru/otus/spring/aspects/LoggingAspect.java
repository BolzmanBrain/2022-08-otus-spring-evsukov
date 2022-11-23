package ru.otus.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Before("@annotation(ru.otus.spring.aspects.LogMethod)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Proxy: " + joinPoint.getThis().getClass().getName());
        System.out.println("Class: " + joinPoint.getTarget().getClass().getName());

        System.out.println("Method call: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(ru.otus.spring.aspects.LogMethod)",
                    returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("Method returned: " + result.toString());
    }
}
