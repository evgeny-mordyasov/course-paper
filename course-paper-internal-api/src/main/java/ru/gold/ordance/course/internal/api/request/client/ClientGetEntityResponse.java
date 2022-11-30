package ru.gold.ordance.course.internal.api.request.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.internal.api.request.Response;

@Builder
@Getter
@ToString
public class ClientGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClient object;

    public static ClientGetEntityResponse success(WebClient client) {
        return ClientGetEntityResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .object(client)
                .build();
    }
}