package ru.gold.ordance.course.base.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.base.utils.ServiceLoggerUtils.*;

@Component
@Aspect
public class ServiceLoggerAspect {
    private ServiceLoggerAspect() {
    }

    @Pointcut("target(ru.gold.ordance.course.base.service.core.BaseSearchService)")
    public void classesThatAreService() {
    }

    @Before(value = "execution(* ru.gold.ordance.course.base.service.*..*(..)) && classesThatAreService()")
    public void registerStartServiceMethod(JoinPoint jp) {
        loggingStartOfServiceMethod(jp.getSignature().toShortString());
    }

    @After(value = "execution(* ru.gold.ordance.course.base.service.*..*(..)) && classesThatAreService()")
    public void registerEndServiceMethod(JoinPoint jp) {
        loggingEndOfServiceMethod(jp.getSignature().toShortString());
    }

    @AfterReturning(value = "execution(* ru.gold.ordance.course.base.service.*..*(..)) && classesThatAreService()", returning = "list")
    public void registerServiceMethodWithListResponse(List<?> list) {
        loggingSizeOfList(list);
    }

    @AfterReturning(value = "execution(* ru.gold.ordance.course.base.service.*..*(..)) && classesThatAreService()", returning = "value")
    public void registerServiceMethodWithLongResponse(Long value) {
        loggingLongValue(value);
    }

    @AfterReturning(value = "execution(* ru.gold.ordance.course.base.service.*..*(..)) && classesThatAreService()", returning = "optional")
    public void registerServiceMethodWithOptionalResponse(Optional<?> optional) {
        loggingOptionalValue(optional);
    }
}
