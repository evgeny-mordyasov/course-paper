package ru.gold.ordance.course.internal.api.domain;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.gold.ordance.course.common.api.Status;
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
            BaseException bs = (BaseException) e;
            return new BaseErrorResponse(new Status(bs.statusCode(), bs.getMessage()));
        }

        return new BaseErrorResponse(Status.error(ExceptionUtils.getStackTrace(e)));
    }
}
