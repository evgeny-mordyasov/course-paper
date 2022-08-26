package ru.gold.ordance.course.web.exception;

import ru.gold.ordance.course.base.exception.BaseException;
import ru.gold.ordance.course.common.api.StatusCode;

public class FileAlreadyExistsException extends BaseException {
    public FileAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.INVALID_RQ;
    }
}
