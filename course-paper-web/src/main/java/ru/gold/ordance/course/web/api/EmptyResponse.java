package ru.gold.ordance.course.web.api;

import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Getter
@ToString
public class EmptyResponse implements Response {
    private final Status status = new Status(StatusCode.SUCCESS);
    private final Object object = null;

    @Override
    public Status getStatus() {
        return status;
    }
}
