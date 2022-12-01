package ru.gold.ordance.course.internal.api.domain.authorization.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.client.model.WebClient;

@Builder
@Getter
@ToString
public class AuthorizationSignInResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClient object;
    private final String token;

    public static AuthorizationSignInResponse success(WebClient client, String token) {
        return AuthorizationSignInResponse.builder()
                .status(Status.success())
                .object(client)
                .token(token)
                .build();
    }
}
