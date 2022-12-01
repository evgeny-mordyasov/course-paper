package ru.gold.ordance.course.internal.api.domain.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.client.model.WebClient;

@Builder
@Getter
@ToString
public class ClientUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClient object;

    public static ClientUpdateResponse success(WebClient client) {
        return ClientUpdateResponse.builder()
                .status(Status.success())
                .object(client)
                .build();
    }
}
