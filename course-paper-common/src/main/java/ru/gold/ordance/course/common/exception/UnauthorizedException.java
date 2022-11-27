package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(StatusCode.UNAUTHORIZED.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.UNAUTHORIZED;
    }
}
