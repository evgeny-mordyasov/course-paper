package ru.gold.ordance.course.web.api;

import lombok.Getter;
import lombok.ToString;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.toStatus;

@Getter
@ToString
public class BaseErrorResponse implements Response {
    private final Status status;

    private BaseErrorResponse(Status status) {
        this.status = status;
    }

    public static BaseErrorResponse createFrom(Exception e) {
        return new BaseErrorResponse(toStatus(e));
    }
}
