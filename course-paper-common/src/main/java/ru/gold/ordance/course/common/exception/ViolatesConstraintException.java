package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public class ViolatesConstraintException extends BaseException {
    public ViolatesConstraintException() {
        super(StatusCode.VIOLATES_CONSTRAINT.getErrorMessage());
    }

    @Override
    public StatusCode statusCode() {
        return StatusCode.VIOLATES_CONSTRAINT;
    }
}
