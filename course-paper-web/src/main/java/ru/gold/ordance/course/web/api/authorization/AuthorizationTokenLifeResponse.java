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
public class AuthorizationTokenLifeResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final boolean isValid;

    public static AuthorizationTokenLifeResponse success(boolean isValid) {
        return AuthorizationTokenLifeResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isValid(isValid)
                .build();
    }

    public static AuthorizationTokenLifeResponse error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AuthorizationTokenLifeResponse.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}
