package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class ValidateException extends BaseException {
    public ValidateException(String message) {
        super(message);
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.INVALID_RQ;
    }
}
