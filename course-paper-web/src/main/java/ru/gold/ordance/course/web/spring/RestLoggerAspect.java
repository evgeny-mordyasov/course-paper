package ru.gold.ordance.course.web.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Response;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.WebLoggerUtils.*;

@Component
@Aspect
public class RestLoggerAspect {
    private RestLoggerAspect() {
    }

    @Pointcut("target(ru.gold.ordance.course.web.service.WebService)")
    public void classesThatAreWebService() {
    }

    @Pointcut("target(ru.gold.ordance.course.web.service.WebService) && args(rq)")
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

    @AfterReturning(value = "execution(* ru.gold.ordance.course.web.service.*..*(..)) && classesThatAreWebServiceWithArg(rq)",
                    returning = "rs",
                    argNames = "rq,rs")
    public void loggingSuccessResponseForWebServiceMethods(Request rq, Response rs) {
        loggingSuccessResponse(rs, rq);
    }

    @AfterReturning(value = "execution(* ru.gold.ordance.course.web.service.*..*()) && classesThatAreWebService()",
            returning = "rs")
    public void loggingSuccessResponseForWebServiceMethods(Response rs) {
        loggingSuccessResponse(rs);
    }

    @AfterThrowing(value = "execution(* ru.gold.ordance.course.web.service.*..*(..)) && classesThatAreWebServiceWithArg(rq)",
            throwing = "e",
            argNames = "rq,e")
    public void loggingErrorResponseForWebServiceMethods(Request rq, Exception e) {
        Response rs = createFrom(e);
        loggingErrorResponse(rs, rq, e);
    }

    @AfterThrowing(value = "execution(* ru.gold.ordance.course.web.service.*..*()) && classesThatAreWebService()",
            throwing = "e")
    public void loggingErrorResponseForWebServiceMethods(Exception e) {
        Response rs = createFrom(e);
        loggingErrorResponse(rs, e);
    }
}
