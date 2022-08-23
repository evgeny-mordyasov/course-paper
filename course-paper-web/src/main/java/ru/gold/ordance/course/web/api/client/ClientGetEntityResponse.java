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
public class ClientGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final WebClient client;

    public static ClientGetEntityResponse success(WebClient client) {
        return status().client(client).build();
    }

    public static ClientGetEntityResponse emptySuccess() {
        return status().build();
    }

    private static ClientGetEntityResponse.ClientGetEntityResponseBuilder status() {
        return ClientGetEntityResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS));
    }
}
