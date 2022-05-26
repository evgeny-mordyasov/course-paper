package ru.gold.ordance.course.web.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface Response extends Serializable {
    Status getStatus();

    @JsonIgnore
    default StatusCode getCode() {
        return getStatus().getCode();
    }

    @JsonIgnore
    default String getErrorMessage() {
        return getStatus().getCode().getErrorMessage();
    }
}
