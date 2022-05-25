package ru.gold.ordance.course.web.api;

import java.io.Serializable;

public interface Response extends Serializable {
    Status getStatus();

    default StatusCode getCode() {
        return getStatus().getCode();
    }

    default String getErrorMessage() {
        return getStatus().getCode().getErrorMessage();
    }
}
