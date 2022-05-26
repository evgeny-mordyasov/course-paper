package ru.gold.ordance.course.web.spring;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Response;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.LoggerUtils.*;

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

    @Before("execution(* ru.gold.ordance.course.web.service.*..findAll()) && classesThatAreWebService()")
    public void loggingFindByAllMethod() {
        loggingFindAll();
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..findById(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingFindByIdMethod(Request rq) {
        loggingFindById(rq);
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..findByName(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingFindByNameMethod(Request rq) {
        loggingFindByName(rq);
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..findByEmail(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingFindByEmailMethod(Request rq) {
        loggingFindByEmail(rq);
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..save(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingSaveMethod(Request rq) {
        loggingSave(rq);
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..update(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingUpdateMethod(Request rq) {
        loggingUpdate(rq);
    }

    @Before(value = "execution(* ru.gold.ordance.course.web.service.*..deleteById(..)) && classesThatAreWebServiceWithArg(rq)",
            argNames = "rq")
    public void loggingDeleteByIdMethod(Request rq) {
        loggingDeleteById(rq);
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
