package ru.gold.ordance.course.web.exception;

import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.common.exception.BaseException;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(StatusCode.UNAUTHORIZED.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.UNAUTHORIZED;
    }
}
