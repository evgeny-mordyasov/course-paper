package ru.gold.ordance.course.base.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class InternalEntityNotFoundException extends BaseException {
    public InternalEntityNotFoundException() {
        super(StatusCode.INTERNAL_ENTITY_NOT_FOUND.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.INTERNAL_ENTITY_NOT_FOUND;
    }
}
