package ru.gold.ordance.course.web.api.authorization;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.Response;

@Builder
@Getter
@ToString
public class AuthorizationConfirmAccountResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static AuthorizationConfirmAccountResponse success() {
        return AuthorizationConfirmAccountResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static AuthorizationConfirmAccountResponse banned() {
        return AuthorizationConfirmAccountResponse.builder()
                .status(new Status().withCode(StatusCode.BANNED).withDescription(StatusCode.BANNED.getErrorMessage()))
                .build();
    }
}
