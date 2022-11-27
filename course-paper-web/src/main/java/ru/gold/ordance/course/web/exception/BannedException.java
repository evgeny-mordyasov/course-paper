package ru.gold.ordance.course.web.exception;

import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.common.exception.BaseException;

public class BannedException extends BaseException {
    public BannedException() {
        super(StatusCode.BANNED.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.BANNED;
    }
}
