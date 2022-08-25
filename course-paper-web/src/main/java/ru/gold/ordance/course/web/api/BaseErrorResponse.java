package ru.gold.ordance.course.web.api;

import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.base.exception.BaseException;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Getter
@ToString
public class BaseErrorResponse implements Response {
    private final Status status;

    private BaseErrorResponse(Status status) {
        this.status = status;
    }

    public static BaseErrorResponse createFrom(Exception e) {
        if (e instanceof BaseException) {
            BaseException exc = (BaseException) e;
            return new BaseErrorResponse(new Status().withCode(exc.statusCode()).withDescription(exc.getMessage()));
        }

        return new BaseErrorResponse(new Status().withCode(StatusCode.CALL_ERROR).withDescription(e.toString()));
    }
}
