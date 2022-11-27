package ru.gold.ordance.course.common.exception;

import ru.gold.ordance.course.common.api.StatusCode;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public abstract StatusCode statusCode();
}
