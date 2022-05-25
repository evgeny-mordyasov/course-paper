package ru.gold.ordance.course.web.api.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

@Builder
@Getter
@ToString
public class ClientUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static ClientUpdateResponse success() {
        return ClientUpdateResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }
}
