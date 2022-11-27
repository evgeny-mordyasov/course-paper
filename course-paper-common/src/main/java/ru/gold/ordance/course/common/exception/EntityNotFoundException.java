package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super(StatusCode.ENTITY_NOT_FOUND.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.ENTITY_NOT_FOUND;
    }
}
