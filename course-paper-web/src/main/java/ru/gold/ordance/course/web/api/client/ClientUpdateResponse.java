package ru.gold.ordance.course.web.api.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Builder
@Getter
@ToString
public class ClientUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClient client;

    public static ClientUpdateResponse success(WebClient client) {
        return ClientUpdateResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .client(client)
                .build();
    }
}
