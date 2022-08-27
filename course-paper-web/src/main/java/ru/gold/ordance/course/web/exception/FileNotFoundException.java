package ru.gold.ordance.course.web.exception;

import ru.gold.ordance.course.base.exception.BaseException;
import ru.gold.ordance.course.common.api.StatusCode;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException(String message) {
        super(message);
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.INVALID_RQ;
    }
}
