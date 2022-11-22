package ru.gold.ordance.course.web.api.authorization;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.client.WebClient;

@Builder
@Getter
@ToString
public class AuthorizationSignUpResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClient client;

    public static AuthorizationSignUpResponse success(WebClient client) {
        return AuthorizationSignUpResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .client(client)
                .build();
    }
}
