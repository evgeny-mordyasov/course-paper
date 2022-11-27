package ru.gold.ordance.course.web.exception;

import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.common.exception.BaseException;

public class ValidateException extends BaseException {
    public ValidateException(String message) {
        super(message);
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.INVALID_RQ;
    }
}
