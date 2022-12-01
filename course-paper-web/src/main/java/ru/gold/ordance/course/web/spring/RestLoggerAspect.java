package ru.gold.ordance.course.web.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.web.utils.WebLoggerUtils.loggingReceivedRequest;

@Component
@Aspect
public class RestLoggerAspect {
    private RestLoggerAspect() {
    }

    @Pointcut("target(ru.gold.ordance.course.web.service.web.WebService)")
    public void classesThatAreWebService() {
    }

    @Pointcut("target(ru.gold.ordance.course.web.service.web.WebService) && args(rq)")
    public void classesThatAreWebServiceWithArg(Request rq) {
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..*()) && classesThatAreWebService()")
    public void loggingWebServiceMethodWithRequestArg(JoinPoint jp) {
        loggingReceivedRequest(jp.getSignature().getName());
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..*(..)) && classesThatAreWebServiceWithArg(rq)", argNames = "jp,rq")
    public void loggingWebServiceMethodWithRequestArg(JoinPoint jp, Request rq) {
        loggingReceivedRequest(rq, jp.getSignature().getName());
    }
}
