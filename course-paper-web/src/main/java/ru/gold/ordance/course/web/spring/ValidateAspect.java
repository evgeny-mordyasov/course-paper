package ru.gold.ordance.course.web.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.internal.api.request.Request;

@Component
@Aspect
public class ValidateAspect {
    private ValidateAspect() {
    }

    @Pointcut("execution(* ru.gold.ordance.course.web.service.*..*(..)) && args(ru.gold.ordance.course.internal.api.request.Request)")
    public void webServiceMethodWithRequestArg() {
    }

    @Pointcut("target(ru.gold.ordance.course.web.service.web.WebService)")
    public void classesThatAreWebService() {
    }


    @Before("classesThatAreWebService() && webServiceMethodWithRequestArg()")
    public void validateRequest(JoinPoint jp) {
        ((Request) jp.getArgs()[0]).validate();
    }
}
