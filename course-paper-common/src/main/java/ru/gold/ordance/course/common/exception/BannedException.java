package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class BannedException extends BaseException {
    public BannedException() {
        super(StatusCode.BANNED.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.BANNED;
    }
}
