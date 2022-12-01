package ru.gold.ordance.course.internal.api.domain.authorization.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.internal.api.domain.Response;

@Builder
@Getter
@ToString
public class AuthorizationConfirmAccountResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static AuthorizationConfirmAccountResponse success() {
        return AuthorizationConfirmAccountResponse.builder()
                .status(Status.success())
                .build();
    }

    public static AuthorizationConfirmAccountResponse banned() {
        return AuthorizationConfirmAccountResponse.builder()
                .status(new Status(StatusCode.BANNED, StatusCode.BANNED.getErrorMessage()))
                .build();
    }
}
