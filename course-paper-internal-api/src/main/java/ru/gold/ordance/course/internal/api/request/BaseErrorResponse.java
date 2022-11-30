package ru.gold.ordance.course.internal.api.request;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.common.exception.BaseException;

@Getter
@ToString
public class BaseErrorResponse implements Response {
    private final Status status;

    private BaseErrorResponse(Status status) {
        this.status = status;
    }

    public static BaseErrorResponse handleException(Exception e) {
        if (e instanceof BaseException) {
            BaseException exc = (BaseException) e;
            return new BaseErrorResponse(new Status().withCode(exc.statusCode()).withDescription(exc.getMessage()));
        }

        return new BaseErrorResponse(new Status().withCode(StatusCode.CALL_ERROR).withDescription(ExceptionUtils.getStackTrace(e)));
    }
}