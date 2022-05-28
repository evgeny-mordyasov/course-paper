package ru.gold.ordance.course.web.api.authorization;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

@Builder
@Getter
@ToString
public class AuthorizationSignInResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final String token;

    public static AuthorizationSignInResponse success(String token) {
        return AuthorizationSignInResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .token(token)
                .build();
    }
}
