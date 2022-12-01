package ru.gold.ordance.course.internal.api.domain;

import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;

@Getter
@ToString
public class EmptySuccessfulResponse implements Response {
    private final Status status = Status.success();
    private final Object object = null;
}
